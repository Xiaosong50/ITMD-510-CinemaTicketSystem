package controller;

import application.Main;
import dao.HallDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import model.Hall;

public class EditHallController {

    @FXML
    private TextField hallIdField;
    @FXML
    private TextField hallTypeField;
    @FXML
    private TextField hallNameField;
    @FXML
    private TextField seatCountField;

    private final HallDAO hallDAO = new HallDAO();
    private static Hall selectedHall;

    public static void setSelectedHall(Hall hall) {
        selectedHall = hall;
    }

    @FXML
    public void initialize() {
        if (selectedHall != null) {
            populateFields();
        }
    }

    private void populateFields() {
        hallIdField.setText(String.valueOf(selectedHall.getHallId()));
        hallNameField.setText(selectedHall.getHallName());
        hallTypeField.setText(selectedHall.getHallType());
        seatCountField.setText(String.valueOf(selectedHall.getSeatCount()));
    }

    @FXML
    private void saveHallChanges() {
        try {
            String hallName = hallNameField.getText();
            String hallType = hallTypeField.getText();
            int seatCount = Integer.parseInt(seatCountField.getText().trim());

            if (hallName.isEmpty() || hallType == null || seatCount <= 0) {
                showAlert("Input Error", "Please make sure all fields are valid. ");
                return;
            }

            // 更新影厅信息
            selectedHall.setHallName(hallName);
            selectedHall.setHallType(hallType);
            selectedHall.setSeatCount(seatCount);

            // 执行更新
            hallDAO.updateHall(selectedHall);
            showAlert("Success", "Hall information updated successfully！");
            goBackToHallManagement();

        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please make sure the number of seats is a number.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while updating hall information, please try again later.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void goBackToHallManagement() {
        Main.setRoot("HallManagementPage");
    }
    
    @FXML
    private void goToAdminHome() {
        Main.setRoot("AdminHomePage");
    }
}