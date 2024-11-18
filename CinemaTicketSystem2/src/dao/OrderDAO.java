package dao;

import model.DBConnection;
import model.MovieSchedule;
import model.Order;
import model.Seat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

	public int createOrder(int userId, MovieSchedule schedule, List<Seat> seats, double totalPrice, String paymentMethod) {
	    String orderQuery = "INSERT INTO orders (user_id, schedule_id, order_date, total_price, payment_id) " +
	                        "VALUES (?, ?, CURRENT_TIMESTAMP, ?, ?)";
	    int orderId = -1;
	    Connection connection = null;

	    try {
	        connection = DBConnection.getConnection();
	        connection.setAutoCommit(false); // 开始事务

	        try (PreparedStatement pstmt = connection.prepareStatement(orderQuery, Statement.RETURN_GENERATED_KEYS)) {

	            pstmt.setInt(1, userId);  // 设置用户ID
	            pstmt.setInt(2, schedule.getScheduleId());
	            pstmt.setDouble(3, totalPrice);
	            pstmt.setInt(4, getPaymentId(paymentMethod, connection));

	            int affectedRows = pstmt.executeUpdate();
	            if (affectedRows == 0) {
	                throw new SQLException("Creating order failed, no rows affected.");
	            }

	            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
	                if (generatedKeys.next()) {
	                    orderId = generatedKeys.getInt(1);
	                    saveOrderSeats(orderId, seats, schedule.getScheduleId(), connection); // 传入 scheduleId
	                }
	            }
	        }

	        connection.commit(); // 提交事务
	    } catch (SQLException e) {
	        e.printStackTrace();
	        if (connection != null) {
	            try {
	                connection.rollback(); // 出现异常时回滚事务
	            } catch (SQLException rollbackEx) {
	                rollbackEx.printStackTrace();
	            }
	        }
	    } finally {
	        if (connection != null) {
	            try {
	                connection.close(); // 确保连接被正确关闭
	            } catch (SQLException closeEx) {
	                closeEx.printStackTrace();
	            }
	        }
	    }
	    return orderId;
	}
	private void saveOrderSeats(int orderId, List<Seat> seats, int scheduleId, Connection connection) throws SQLException {
	    String orderSeatQuery = "INSERT INTO order_seat (order_id, seat_schedule_id) VALUES (?, ?)";
	    try (PreparedStatement pstmt = connection.prepareStatement(orderSeatQuery)) {
	        for (Seat seat : seats) {
	            pstmt.setInt(1, orderId);
	            pstmt.setInt(2, seat.getSeatId());
	            pstmt.executeUpdate();

	            // 更新座位状态为已售出
	            markSeatAsSold(scheduleId, seat.getSeatId(), connection);
	        }
	    }
	}

	// 更新座位状态为已售出的方法
	private void markSeatAsSold(int scheduleId, int seatId, Connection connection) throws SQLException {
	    String updateQuery = "UPDATE seat_schedule SET is_seat_sold = TRUE WHERE schedule_id = ? AND seat_id = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(updateQuery)) {
	        pstmt.setInt(1, scheduleId);
	        pstmt.setInt(2, seatId);
	        pstmt.executeUpdate();
	    }
	}

	private int getPaymentId(String paymentMethod, Connection connection) throws SQLException {
	    String paymentQuery = "SELECT payment_id FROM payment WHERE payment_name = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(paymentQuery)) {
	        pstmt.setString(1, paymentMethod);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            return rs.getInt("payment_id");
	        } else {
	            throw new SQLException("Payment method not found: " + paymentMethod);
	        }
	    }
	}    
	public List<Order> getOrdersByUserId(int userId) {
	    List<Order> orders = new ArrayList<>();
	    String query = "SELECT o.order_id,o.user_id, m.title AS movie_title, ms.start_time, ms.end_time, m.duration, " +
                "c.cinema_name, h.hall_name, GROUP_CONCAT(s.seat_number) AS seat_numbers, o.total_price, " +
                "p.payment_name AS payment_method, o.order_date " +
                "FROM orders o " +
                "JOIN movie_schedule ms ON o.schedule_id = ms.schedule_id " +
                "JOIN movie m ON ms.movie_id = m.movie_id " +
                "JOIN hall h ON ms.hall_id = h.hall_id " +
                "JOIN cinema c ON h.cinema_id = c.cinema_id " +
                "JOIN order_seat os ON o.order_id = os.order_id " +
                "JOIN seat s ON os.seat_schedule_id = s.seat_id " +
                "JOIN payment p ON o.payment_id = p.payment_id " +
                "WHERE o.user_id = ? " +
                "GROUP BY o.order_id";

	    try (Connection connection = DBConnection.getConnection();
	         PreparedStatement pstmt = connection.prepareStatement(query)) {

	        pstmt.setInt(1, userId);
	        ResultSet rs = pstmt.executeQuery();

	        while (rs.next()) {
	            orders.add(new Order(
	            		rs.getInt("order_id"),
	            		rs.getInt("user_id"),
	                    rs.getString("movie_title"),
	                    rs.getString("cinema_name"),
	                    rs.getString("hall_name"),
	                    rs.getString("seat_numbers"),
	                    rs.getString("start_time"),
	                    rs.getString("end_time"),
	                    rs.getInt("duration"),
	                    rs.getDouble("total_price"),
	                    rs.getString("payment_method"),
	                    rs.getString("order_date")
	            ));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return orders;
	}
	
	public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT o.order_id, o.user_id, m.title AS movie_title, m.duration, ms.start_time, ms.end_time, " +
                       "c.cinema_name, h.hall_name, GROUP_CONCAT(s.seat_number) AS seat_numbers, o.total_price, " +
                       "p.payment_name AS payment_method, o.order_date " +
                       "FROM orders o " +
                       "JOIN movie_schedule ms ON o.schedule_id = ms.schedule_id " +
                       "JOIN movie m ON ms.movie_id = m.movie_id " +
                       "JOIN hall h ON ms.hall_id = h.hall_id " +
                       "JOIN cinema c ON h.cinema_id = c.cinema_id " +
                       "JOIN order_seat os ON o.order_id = os.order_id " +
                       "JOIN seat s ON os.seat_schedule_id = s.seat_id " +
                       "JOIN payment p ON o.payment_id = p.payment_id " +
                       "GROUP BY o.order_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Order order = new Order(
                		rs.getInt("order_id"),
	            		rs.getInt("user_id"),
	                    rs.getString("movie_title"),
	                    rs.getString("cinema_name"),
	                    rs.getString("hall_name"),
	                    rs.getString("seat_numbers"),
	                    rs.getString("start_time"),
	                    rs.getString("end_time"),
	                    rs.getInt("duration"),
	                    rs.getDouble("total_price"),
	                    rs.getString("payment_method"),
	                    rs.getString("order_date")
                );
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
}