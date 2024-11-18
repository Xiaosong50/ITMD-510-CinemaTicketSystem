package controller;

import application.Main;
import dao.CinemaDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Cinema;

import java.util.List;

public class CinemaManagementController {

    @FXML
    private TableView<Cinema> cinemaTable;
    @FXML
    private TableColumn<Cinema, Integer> cinemaIdColumn;
    @FXML
    private TableColumn<Cinema, String> cinemaNameColumn;
    @FXML
    private TableColumn<Cinema, String> cinemaAddressColumn;
    @FXML
    private TableColumn<Cinema, String> cinemaPhoneColumn;

    private final CinemaDAO cinemaDAO = new CinemaDAO();

    @FXML
    public void initialize() {
        cinemaIdColumn.setCellValueFactory(new PropertyValueFactory<>("cinemaId"));
        cinemaNameColumn.setCellValueFactory(new PropertyValueFactory<>("cinemaName"));
        cinemaAddressColumn.setCellValueFactory(new PropertyValueFactory<>("cinemaAddress"));
        cinemaPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("cinemaPhone"));

        loadCinemas();
    }

    private void loadCinemas() {
        List<Cinema> cinemas = cinemaDAO.getAllCinemas();
        cinemaTable.setItems(FXCollections.observableArrayList(cinemas));
    }

    @FXML
    private void addCinema() {
        Main.setRoot("AddCinemaPage"); 
    }

    @FXML
    private void editCinema() {
        Cinema selectedCinema = cinemaTable.getSelectionModel().getSelectedItem();
        if (selectedCinema != null) {
            EditCinemaController.setSelectedCinema(selectedCinema);
            Main.setRoot("EditCinemaPage"); 
        } else {
            showAlert("Select Cinema", "Please select a cinema to edit first.");
        }
    }

    @FXML
    private void deleteCinema() {
        Cinema selectedCinema = cinemaTable.getSelectionModel().getSelectedItem();
        if (selectedCinema != null) {
            cinemaDAO.deleteCinema(selectedCinema.getCinemaId());
            loadCinemas(); 
        } else {
            showAlert("Select Cinema", "Please select a cinema to delete first.");
        }
    }

    @FXML
    private void manageHalls() {
        Cinema selectedCinema = cinemaTable.getSelectionModel().getSelectedItem();
        if (selectedCinema != null) {
            HallManagementController.setSelectedCinema(selectedCinema);
            Main.setRoot("HallManagementPage"); 
        } else {
            showAlert("Select Cinema", "Please select a cinema to manage the hall first");
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
    private void goToAdminHome() {
        Main.setRoot("AdminHomePage");
    }

    @FXML
    private void goToAdminProfile() {
        Main.setRoot("AdminProfilePage");
    }
}