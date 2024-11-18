package dao;

import model.User;
import model.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

	public User validateLogin(String username, String password) {
	    String sql = "SELECT * FROM users WHERE user_name = ? AND user_password = ?";
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setString(1, username);
	        stmt.setString(2, password);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            return new User(
	                    rs.getInt("user_id"),
	                    rs.getString("user_name"),
	                    rs.getString("user_password"),
	                    rs.getString("user_gender"),
	                    rs.getString("user_phone"),
	                    rs.getString("user_email")
	            );
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
    public boolean registerUser(String username, String password, String gender, String email, String phone) {
        String sql = "INSERT INTO users (user_name, user_password, user_gender, user_email, user_phone) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, gender);
            stmt.setString(4, email);
            stmt.setString(5, phone);

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0; // Returns true if insertion was successful

        } catch (Exception e) {
            e.printStackTrace();
            return false; // Returns false if insertion failed
        }
    }
    
    public User getUser(int userId) {
        String query = "SELECT * FROM users WHERE user_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getInt("user_id"),
                    rs.getString("user_name"),
                    rs.getString("user_password"),
                    rs.getString("user_gender"),
                    rs.getString("user_phone"),
                    rs.getString("user_email")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateUser(User user) {
        String query = "UPDATE users SET user_name = ?, user_password = ?, user_gender = ?, user_phone = ?, user_email = ? WHERE user_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getUserPassword());
            pstmt.setString(3, user.getUserGender());
            pstmt.setString(4, user.getUserPhone());
            pstmt.setString(5, user.getUserEmail());
            pstmt.setInt(6, user.getUserId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    
}