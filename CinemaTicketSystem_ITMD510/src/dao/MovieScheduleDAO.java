package dao;

import model.DBConnection;
import model.MovieSchedule;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MovieScheduleDAO {


	public void addSchedule(MovieSchedule schedule) {
	    String scheduleQuery = "INSERT INTO xl_movie_schedule (movie_id, hall_id, start_time, end_time, ticket_price) VALUES (?, ?, ?, ?, ?)";
	    String seatScheduleQuery = "INSERT INTO xl_seat_schedule (schedule_id, seat_id, is_seat_sold) VALUES (?, ?, false)";
	    
	    try (Connection connection = DBConnection.getConnection();
	         PreparedStatement scheduleStmt = connection.prepareStatement(scheduleQuery, Statement.RETURN_GENERATED_KEYS);
	         PreparedStatement seatScheduleStmt = connection.prepareStatement(seatScheduleQuery)) {

	        // 插入排片记录
	        scheduleStmt.setInt(1, schedule.getMovieId());
	        scheduleStmt.setInt(2, schedule.getHallId());
	        scheduleStmt.setTimestamp(3, Timestamp.valueOf(schedule.getStartTime()));
	        scheduleStmt.setTimestamp(4, Timestamp.valueOf(schedule.getEndTime()));
	        scheduleStmt.setDouble(5, schedule.getTicketPrice());
	        int affectedRows = scheduleStmt.executeUpdate();

	        if (affectedRows > 0) {
	            // 获取新排片的 schedule_id
	            ResultSet generatedKeys = scheduleStmt.getGeneratedKeys();
	            if (generatedKeys.next()) {
	                int scheduleId = generatedKeys.getInt(1);

	                // 获取该影厅的所有座位ID
	                List<Integer> seatIds = getSeatIdsByHallId(schedule.getHallId(), connection);

	                // 插入座位安排
	                for (int seatId : seatIds) {
	                    seatScheduleStmt.setInt(1, scheduleId);
	                    seatScheduleStmt.setInt(2, seatId);
	                    seatScheduleStmt.addBatch();
	                }
	                seatScheduleStmt.executeBatch();
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	private List<Integer> getSeatIdsByHallId(int hallId, Connection connection) throws SQLException {
	    List<Integer> seatIds = new ArrayList<>();
	    String query = "SELECT seat_id FROM xl_seat WHERE hall_id = ?";

	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setInt(1, hallId);
	        ResultSet rs = pstmt.executeQuery();
	        while (rs.next()) {
	            seatIds.add(rs.getInt("seat_id"));
	        }
	    }
	    return seatIds;
	}
	public  boolean isScheduleTimeValid(int hallId, LocalDateTime startTime, LocalDateTime endTime) {
        List<MovieSchedule> existingSchedules = getSchedulesByHallId(hallId);

        for (MovieSchedule schedule : existingSchedules) {
            LocalDateTime existingStart = schedule.getStartTime();
            LocalDateTime existingEnd = schedule.getEndTime();

            if ((startTime.isBefore(existingEnd.plusMinutes(10)) && endTime.isAfter(existingStart.minusMinutes(10)))) {
                return false;
            }
        }
        return true;
    }
    
    public  List<MovieSchedule> getSchedulesByHallId(int hallId) {
        List<MovieSchedule> schedules = new ArrayList<>();
        String query = "SELECT * FROM xl_movie_schedule WHERE hall_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, hallId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                schedules.add(new MovieSchedule(
                        rs.getInt("schedule_id"),
                        rs.getInt("movie_id"),
                        "", hallId, "", "", "",
                        rs.getTimestamp("start_time").toLocalDateTime(),
                        rs.getTimestamp("end_time").toLocalDateTime(),
                        rs.getDouble("ticket_price"),
                        0
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedules;
    }

    public void updateSchedule(MovieSchedule schedule) {
        String query = "UPDATE xl_movie_schedule SET hall_id = ?, start_time = ?, end_time = ?, ticket_price = ? WHERE schedule_id = ?";
	    Connection connection = null;

        try {
        	connection = DBConnection.getConnection();
            connection.setAutoCommit(false); // 开始事务

            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setInt(1, schedule.getHallId());
                pstmt.setTimestamp(2, Timestamp.valueOf(schedule.getStartTime()));
                pstmt.setTimestamp(3, Timestamp.valueOf(schedule.getEndTime()));
                pstmt.setDouble(4, schedule.getTicketPrice());
                pstmt.setInt(5, schedule.getScheduleId());
                pstmt.executeUpdate();
            }

            // 检查是否需要更新座位安排
            if (isHallUpdated(schedule)) {
                updateSeatSchedule(connection, schedule.getScheduleId(), schedule.getHallId());
            }

            connection.commit(); // 提交事务
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback(); // 出现异常时回滚
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        }
    }

    // 检查影厅是否更新
    private boolean isHallUpdated(MovieSchedule schedule) {
        MovieSchedule existingSchedule = getScheduleById(schedule.getScheduleId());
        return existingSchedule != null && existingSchedule.getHallId() != schedule.getHallId();
    }

    // 更新座位安排
    private void updateSeatSchedule(Connection connection, int scheduleId, int newHallId) throws SQLException {
        // 删除旧的座位安排
        String deleteQuery = "DELETE FROM xl_seat_schedule WHERE schedule_id = ?";
        try (PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery)) {
            deleteStmt.setInt(1, scheduleId);
            deleteStmt.executeUpdate();
        }

        // 添加新的座位安排
        String insertQuery = "INSERT INTO xl_seat_schedule (schedule_id, seat_id, is_seat_sold) VALUES (?, ?, false)";
        try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
            List<Integer> seatIds = getSeatIdsByHallId(newHallId, connection);
            for (int seatId : seatIds) {
                insertStmt.setInt(1, scheduleId);
                insertStmt.setInt(2, seatId);
                insertStmt.addBatch();
            }
            insertStmt.executeBatch();
        }
    }

    // 根据ID获取排片信息
    private MovieSchedule getScheduleById(int scheduleId) {
        String query = "SELECT * FROM xl_movie_schedule WHERE schedule_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, scheduleId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new MovieSchedule(
                            rs.getInt("schedule_id"),
                            rs.getInt("movie_id"),
                            "", rs.getInt("hall_id"), "", "", "",
                            rs.getTimestamp("start_time").toLocalDateTime(),
                            rs.getTimestamp("end_time").toLocalDateTime(),
                            rs.getDouble("ticket_price"),
                            0
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteSchedule(int scheduleId) {
        String query = "DELETE FROM xl_movie_schedule WHERE schedule_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, scheduleId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}