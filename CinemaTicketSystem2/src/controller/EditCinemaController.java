package controller;

import application.Main;
import dao.CinemaDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import model.Cinema;

public class EditCinemaController {

    @FXML
    private TextField cinemaIdField;
    @FXML
    private TextField cinemaNameField;
    @FXML
    private TextField cinemaAddressField;
    @FXML
    private TextField cinemaPhoneField;

    private final CinemaDAO cinemaDAO = new CinemaDAO();
    private static Cinema selectedCinema;

    public static void setSelectedCinema(Cinema cinema) {
        selectedCinema = cinema;
    }

    @FXML
    public void initialize() {
        if (selectedCinema != null) {
            populateFields();
        }
    }

    private void populateFields() {
        cinemaIdField.setText(String.valueOf(selectedCinema.getCinemaId()));
        cinemaNameField.setText(selectedCinema.getCinemaName());
        cinemaAddressField.setText(selectedCinema.getCinemaAddress());
        cinemaPhoneField.setText(selectedCinema.getCinemaPhone());
    }

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

        // 更新影院信息
        selectedCinema.setCinemaName(name);
        selectedCinema.setCinemaAddress(address);
        selectedCinema.setCinemaPhone(phone);

        cinemaDAO.updateCinema(selectedCinema);

        showAlert("Success", "Cimema information has been updated！");
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