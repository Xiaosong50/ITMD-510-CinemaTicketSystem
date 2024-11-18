package controller;

import application.Main;
import dao.HallDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import model.Cinema;
import model.Hall;

public class AddHallController {

    @FXML
    private TextField cinemaIdField;
    @FXML
    private TextField hallNameField;
    @FXML
    private TextField hallTypeField;
    @FXML
    private TextField seatCountField;

    private final HallDAO hallDAO = new HallDAO();
    private static Cinema selectedCinema;

    public static void setCinema(Cinema cinema) {
        selectedCinema = cinema;
    }

    @FXML
    public void initialize() {
        if (selectedCinema != null) {
            cinemaIdField.setText(String.valueOf(selectedCinema.getCinemaId()));
        }
    }

    @FXML
    private void saveHall() {
        String hallName = hallNameField.getText().trim();
        String hallType = hallTypeField.getText().trim();
        String seatCountText = seatCountField.getText().trim();

        if (hallName.isEmpty() || hallType.isEmpty() || seatCountText.isEmpty()) {
            showAlert("Input Error", "Please fill in all fields！");
            return;
        }

        int seatCount;
        try {
            seatCount = Integer.parseInt(seatCountText);
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please make sure the number of seats is a number.");
            return;
        }

        Hall hall = new Hall(0,selectedCinema.getCinemaId(), hallName, hallType, seatCount);
        
        boolean isAdded = hallDAO.addHall(hall);
        
        if (isAdded) {
            showAlert("Success", "Hall add succeed！");
            goBackToHallManagement();
        } else {
            showAlert("Error", "An error occurred while adding the theater, please try again later.");
        }
    }

    @FXML
    private void goBackToAdminHome() {
        Main.setRoot("AdminHomePage");
    }
    @FXML
    private void goBackToHallManagement() {
        Main.setRoot("HallManagementPage");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}