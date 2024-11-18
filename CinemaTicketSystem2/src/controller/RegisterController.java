package controller;

import dao.UserDAO;
import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class RegisterController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField genderField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private Label statusLabel;

    private UserDAO userDAO = new UserDAO();

    @FXML
    private void handleRegisterAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String gender = genderField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Username and Password are required.");
            statusLabel.setTextFill(Color.RED);
            return;
        }

        boolean isRegistered = userDAO.registerUser(username, password, gender, email, phone);

        if (isRegistered) {
            statusLabel.setText("Registration successful! Please return to the login page.");
            statusLabel.setTextFill(Color.GREEN);
        } else {
            statusLabel.setText("Registration failed. Please try again.");
            statusLabel.setTextFill(Color.RED);
        }
    }

    @FXML
    private void handleBackAction() {
        Main.setRoot("Login");
    }
}