package dao;

import model.Admin;
import model.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {

	public Admin validateAdminLogin(String username, String password) {
		String sql = "SELECT * FROM admins WHERE admin_name = ? AND admin_password = ?";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return new Admin(rs.getInt("admin_id"), rs.getString("admin_name"), rs.getString("admin_password"),
						rs.getString("admin_phone"), rs.getString("admin_position"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	public List<Admin> getAdminsByManager(int managerId) {
		List<Admin> admins = new ArrayList<>();
		String query = "SELECT a.admin_id, a.admin_name, a.admin_phone, a.admin_position " +
				"FROM admins a " +
				"JOIN admin_management am ON a.admin_id = am.admin_id " +
				"WHERE am.manager_id = ?";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(query)) {

			pstmt.setInt(1, managerId);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				admins.add(new Admin(rs.getInt("admin_id"), rs.getString("admin_name"), null,
						rs.getString("admin_phone"), rs.getString("admin_position")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return admins;
	}

	public int isManager(int adminId) {
		int count=0;
		String query = "select count(manager_id) as count from admin_management where manager_id=?";
		try (Connection connection = DBConnection.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(query)) {

			pstmt.setInt(1, adminId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt("count");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}


	public void addAdmin(Admin admin, int managerId) {
		String adminQuery = "INSERT INTO admins (admin_name, admin_password, admin_phone, admin_position) VALUES (?, ?, ?, ?)";
		String managementQuery = "INSERT INTO admin_management (manager_id, admin_id) VALUES (?, ?)";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement adminStmt = connection.prepareStatement(adminQuery, Statement.RETURN_GENERATED_KEYS);
				PreparedStatement managementStmt = connection.prepareStatement(managementQuery)) {

			connection.setAutoCommit(false);

			adminStmt.setString(1, admin.getAdminName());
			adminStmt.setString(2, admin.getAdminPassword());
			adminStmt.setString(3, admin.getAdminPhone());
			adminStmt.setString(4, admin.getAdminPosition());
			adminStmt.executeUpdate();

			ResultSet generatedKeys = adminStmt.getGeneratedKeys();
			if (generatedKeys.next()) {
				int adminId = generatedKeys.getInt(1);
				admin.setAdminId(adminId);

				managementStmt.setInt(1, managerId);
				managementStmt.setInt(2, adminId);
				managementStmt.executeUpdate();
			}

			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateAdminPosition(Admin admin) {
		String query = "UPDATE admins SET admin_position = ? WHERE admin_id = ?";
		try (Connection connection = DBConnection.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(query)) {

			pstmt.setString(1, admin.getAdminPosition());
			pstmt.setInt(2, admin.getAdminId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteAdmin(int adminId) {
		String query = "DELETE FROM admins WHERE admin_id = ?";
		try (Connection connection = DBConnection.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(query)) {

			pstmt.setInt(1, adminId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Admin getAdminById(int adminId) {
        String query = "SELECT * FROM admins WHERE admin_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setInt(1, adminId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Admin(
                    rs.getInt("admin_id"),
                    rs.getString("admin_name"),
                    rs.getString("admin_password"),
                    rs.getString("admin_phone"),
                    rs.getString("admin_position")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
	
	public boolean updateAdmin(Admin admin) {
        String query = "UPDATE admins SET admin_name = ?, admin_password = ?, admin_phone = ? WHERE admin_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, admin.getAdminName());
            pstmt.setString(2, admin.getAdminPassword());
            pstmt.setString(3, admin.getAdminPhone());
            pstmt.setInt(4, admin.getAdminId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}