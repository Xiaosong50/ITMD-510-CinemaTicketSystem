package dao;

import model.DBConnection;
import model.Hall;
import model.Seat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeatDAO {

	public List<Seat> getSeatsForSchedule(int scheduleId) {
		List<Seat> seats = new ArrayList<>();
		String query = "SELECT s.seat_id, s.seat_number, ss.is_seat_sold " + "FROM seat s "
				+ "JOIN seat_schedule ss ON s.seat_id = ss.seat_id " + "WHERE ss.schedule_id = ?";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(query)) {

			pstmt.setInt(1, scheduleId);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Seat seat = new Seat(rs.getInt("seat_id"), rs.getString("seat_number"), rs.getBoolean("is_seat_sold"));
				seats.add(seat);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return seats;
	}

	public static boolean addSeatsForHall(Hall hall) {
		String seatQuery = "INSERT INTO seat (hall_id, seat_number) VALUES (?, ?)";
		String snum;
		char srow='A';
		int j=1;
		try (Connection connection = DBConnection.getConnection();
				PreparedStatement seatStmt = connection.prepareStatement(seatQuery)) {

			for (int i = 1; i <= hall.getSeatCount(); i++) {
				snum = srow+String.valueOf(j);
				seatStmt.setInt(1, hall.getHallId());
				seatStmt.setString(2, snum);
				seatStmt.addBatch();
				j=j+1;
				if (j>10) {
					j=1;
					srow++;
				}
			}
			seatStmt.executeBatch();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static void updateSeatForHall(Hall hall) {
		deleteSeatByHallId(hall.getHallId());
		addSeatsForHall(hall);
	}
	
	public static void deleteSeatByHallId(int hallId) {
        String query = "DELETE FROM seat WHERE hall_id = ?";
        
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            
            pstmt.setInt(1, hallId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
}