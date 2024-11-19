package dao;

import model.Cinema;
import model.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CinemaDAO {

	public List<Cinema> getAllCinemas() {
		List<Cinema> cinemas = new ArrayList<>();
		String query = "SELECT cinema_id, cinema_name, cinema_address, cinema_phone FROM xl_cinema";

		try (Connection connection = DBConnection.getConnection();
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {

			while (rs.next()) {
				cinemas.add(new Cinema(rs.getInt("cinema_id"), rs.getString("cinema_name"),
						rs.getString("cinema_address"), rs.getString("cinema_phone")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cinemas;
	}

	public Cinema getCinemaByHallId(int hallId) {
		Cinema cinema = new Cinema();
		String query = "select c.cinema_id,c.cinema_name,cinema_address, cinema_phone from xl_cinema c join xl_hall h on c.cinema_id=h.cinema_id "
				+ "where h.hall_id=?";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(query)) {

			pstmt.setInt(1, hallId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				cinema = new Cinema(rs.getInt("cinema_id"), rs.getString("cinema_name"), rs.getString("cinema_address"),
						rs.getString("cinema_phone"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cinema;
	}

	public void addCinema(Cinema cinema) {
		String query = "INSERT INTO xl_cinema (cinema_name, cinema_address, cinema_phone) VALUES (?, ?, ?)";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(query)) {

			pstmt.setString(1, cinema.getCinemaName());
			pstmt.setString(2, cinema.getCinemaAddress());
			pstmt.setString(3, cinema.getCinemaPhone());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateCinema(Cinema cinema) {
		String query = "UPDATE xl_cinema SET cinema_name = ?, cinema_address = ?, cinema_phone = ? WHERE cinema_id = ?";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(query)) {

			pstmt.setString(1, cinema.getCinemaName());
			pstmt.setString(2, cinema.getCinemaAddress());
			pstmt.setString(3, cinema.getCinemaPhone());
			pstmt.setInt(4, cinema.getCinemaId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteCinema(int cinemaId) {
		String query = "DELETE FROM xl_cinema WHERE cinema_id = ?";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(query)) {

			pstmt.setInt(1, cinemaId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}