package controller;

import dao.UserDAO;
import dao.AdminDAO;
import model.User;
import model.Admin;
import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label statusLabel;

    @FXML
    private CheckBox adminCheckBox;

    private UserDAO userDAO = new UserDAO();
    private AdminDAO adminDAO = new AdminDAO();

    @FXML
    private void handleLoginAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Please enter both Username and Password.");
            statusLabel.setTextFill(Color.RED);
            return;
        }

        if (adminCheckBox.isSelected()) {
            // Admin login
            Admin admin = adminDAO.validateAdminLogin(username, password);
            if (admin != null) {
                statusLabel.setText("Admin login successful! Welcome " + admin.getAdminName() + ".");
                statusLabel.setTextFill(Color.GREEN);
                Main.setCurrentAdminId(admin.getAdminId());
                Main.setRoot("AdminHomePage");
            } else {
                statusLabel.setText("Invalid Admin Username or Password.");
                statusLabel.setTextFill(Color.RED);
            }
        } else {
            // User login
            User user = userDAO.validateLogin(username, password);
            if (user != null) {
                statusLabel.setText("User login successful! Welcome " + user.getUserName() + ".");
                statusLabel.setTextFill(Color.GREEN);
                Main.setCurrentUserId(user.getUserId());
                Main.setRoot("SystemHome");
            } else {
                statusLabel.setText("Invalid Username or Password.");
                statusLabel.setTextFill(Color.RED);
            }
        }
    }

    @FXML
    private void handleRegisterAction() {
        Main.setRoot("Register");
    }
}