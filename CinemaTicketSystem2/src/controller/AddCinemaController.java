package controller;

import application.Main;
import dao.CinemaDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import model.Cinema;

public class AddCinemaController {

    @FXML
    private TextField cinemaNameField;
    @FXML
    private TextField cinemaAddressField;
    @FXML
    private TextField cinemaPhoneField;

    private final CinemaDAO cinemaDAO = new CinemaDAO();

    @FXML
    private void saveCinema() {
        String name = cinemaNameField.getText().trim();
        String address = cinemaAddressField.getText().trim();
        String phone = cinemaPhoneField.getText().trim();

        // 检查输入是否有效
        if (name.isEmpty() || address.isEmpty() || phone.isEmpty()) {
            showAlert("Input Error", "Please fill in all fields！");
            return;
        }

        // 创建并保存新影院
        Cinema cinema = new Cinema(0, name, address, phone);  
        cinemaDAO.addCinema(cinema);
        
        showAlert("Success", "Cinema add succeed！");
        goBackToCinemaManagement();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void goBackToCinemaManagement() {
        Main.setRoot("CinemaManagementPage");
    }
}