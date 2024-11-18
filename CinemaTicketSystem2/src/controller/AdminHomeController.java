package controller;

import application.Main;
import dao.AdminDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class AdminHomeController {
	private AdminDAO adminDAO;

	 @FXML
	    public void initialize() {
	        // 初始化 adminDAO
	        adminDAO = new AdminDAO();
	    }

    @FXML
    private void goToAdminHome() {
        Main.setRoot("AdminHomePage");
    }

    @FXML
    private void goToAdminProfile() {
        Main.setRoot("AdminProfilePage"); 
    }

    @FXML
    private void goToMovieManagement() {
        Main.setRoot("MovieManagementPage");
    }

    @FXML
    private void goToCinemaManagement() {
        Main.setRoot("CinemaManagementPage");
    }

    @FXML
    private void goToOrderManagement() {
        Main.setRoot("OrderManagementPage");
    }

    @FXML
    private void goToAdminManagement() {
    	if (adminDAO.isManager(Main.getCurrentAdminId())==0) {
			showAlert("Permission Error", "You don't have permission to access the administrator management page.");
//			goToAdminHome();
			return;
		}
        Main.setRoot("AdminManagementPage");
    }
    
    private void showAlert(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
}