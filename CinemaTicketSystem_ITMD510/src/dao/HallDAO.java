package dao;

import model.DBConnection;
import model.Hall;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HallDAO {

    public List<Hall> getHallsByCinemaId(int cinemaId) {
        List<Hall> halls = new ArrayList<>();
        String query = "SELECT hall_id,cinema_id, hall_name, hall_type, seat_count FROM xl_hall WHERE cinema_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setInt(1, cinemaId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                halls.add(new Hall(rs.getInt("hall_id"),rs.getInt("cinema_id"),
                		rs.getString("hall_name"), rs.getString("hall_type"),rs.getInt("seat_count")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return halls;
    }
    
    public boolean addHall(Hall hall) {
        String query = "INSERT INTO xl_hall (cinema_id, hall_name, hall_type, seat_count) VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setInt(1, hall.getCinemaId());
            pstmt.setString(2, hall.getHallName());
            pstmt.setString(3, hall.getHallType());
            pstmt.setInt(4, hall.getSeatCount());

            int affectedRows = pstmt.executeUpdate();
            
            SeatDAO.addSeatsForHall(getNewHallBycinemaId(hall.getCinemaId()));
            
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Hall getNewHallBycinemaId(int cinemaId) {
    	Hall hall = new Hall();
        String query = "SELECT hall_id,cinema_id, hall_name, hall_type, seat_count FROM xl_hall WHERE cinema_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setInt(1, cinemaId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                hall=new Hall(rs.getInt("hall_id"),rs.getInt("cinema_id"),
                		rs.getString("hall_name"), rs.getString("hall_type"),rs.getInt("seat_count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	return hall;
    }
    
    public void updateHall(Hall hall) {
        int seatCount = getSeatCountByHall(hall);
        String query = "UPDATE xl_hall SET hall_name = ?, hall_type = ?, seat_count = ? WHERE hall_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            
            pstmt.setString(1, hall.getHallName());
            pstmt.setString(2, hall.getHallType());
            pstmt.setInt(3, hall.getSeatCount());
            pstmt.setInt(4, hall.getHallId());
            pstmt.executeUpdate();
            
            if(seatCount!=hall.getSeatCount()) {
                SeatDAO.updateSeatForHall(hall); // 调整座位数量
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public int getSeatCountByHall(Hall hall){
    	int seatCount = 0;
    	String query = "SELECT seat_count FROM xl_hall WHERE hall_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setInt(1, hall.getHallId());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                seatCount = rs.getInt("seat_count");
            }        } catch (SQLException e) {
            e.printStackTrace();

        }
    	return seatCount;
    }
    
    public void deleteHall(int hallId) {
        String query = "DELETE FROM xl_hall WHERE hall_id = ?";
        
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            
            pstmt.setInt(1, hallId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}