package controller;

import application.Main;
import dao.AdminDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Admin;

public class AdminProfileController {

    @FXML
    private TextField adminNameField;
    @FXML
    private PasswordField adminPasswordField;
    @FXML
    private TextField adminPhoneField;
    @FXML
    private TextField adminPositionField;

    private AdminDAO adminDAO;

    public AdminProfileController() {
        adminDAO = new AdminDAO();
    }

    @FXML
    public void initialize() {
        loadAdminInfo();
    }

    private void loadAdminInfo() {
        Admin admin = adminDAO.getAdminById(Main.getCurrentAdminId());
        if (admin != null) {
            adminNameField.setText(admin.getAdminName());
            adminPasswordField.setText(admin.getAdminPassword());
            adminPhoneField.setText(admin.getAdminPhone());
            adminPositionField.setText(admin.getAdminPosition());
        }
    }

    @FXML
    private void updateAdminInfo() {
        String adminName = adminNameField.getText().trim();
        String password = adminPasswordField.getText().trim();
        String phone = adminPhoneField.getText().trim();

        if (adminName.isEmpty() || password.isEmpty() || phone.isEmpty()) {
            showAlert("Input Error", "Please fill in all fields.");
            return;
        }

        Admin admin = new Admin(Main.getCurrentAdminId(), adminName, password, phone,null);
        boolean success = adminDAO.updateAdmin(admin);

        if (success) {
            showAlert("Success", "Administrator information has been updated.");
        } else {
            showAlert("Error", "An error occurred while updating administrator information.");
        }
    }

    @FXML
    private void goToAdminHome() {
        Main.setRoot("AdminHomePage");
    }

    @FXML
    private void goToAdminProfilePage() {
        Main.setRoot("AdminProfilePage");
    }
    
    @FXML
    private void logOut() {
        Main.setRoot("Login");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}