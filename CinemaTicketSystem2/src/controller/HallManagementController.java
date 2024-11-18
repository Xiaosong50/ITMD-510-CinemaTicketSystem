package controller;

import application.Main;
import dao.HallDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Cinema;
import model.Hall;

import java.util.List;

public class HallManagementController {

	@FXML
	private TableView<Hall> hallTable;
	@FXML
	private TableColumn<Hall, Integer> hallIdColumn;
	@FXML
	private TableColumn<Hall, String> hallNameColumn;
	@FXML
	private TableColumn<Hall, String> hallTypeColumn;
	@FXML
	private TableColumn<Hall, Integer> seatCountColumn;

	private final HallDAO hallDAO = new HallDAO();
	private static Cinema selectedCinema;

	public static void setSelectedCinema(Cinema cinema) {
		selectedCinema = cinema;
	}

	@FXML
	public void initialize() {
		hallIdColumn.setCellValueFactory(new PropertyValueFactory<>("hallId"));
		hallNameColumn.setCellValueFactory(new PropertyValueFactory<>("hallName"));
		hallTypeColumn.setCellValueFactory(new PropertyValueFactory<>("hallType"));
		seatCountColumn.setCellValueFactory(new PropertyValueFactory<>("seatCount"));

		loadHalls();
	}

	private void loadHalls() {
		if (selectedCinema != null) {
			List<Hall> halls = hallDAO.getHallsByCinemaId(selectedCinema.getCinemaId());
			hallTable.setItems(FXCollections.observableArrayList(halls));
		}
	}

	@FXML
	private void addHall() {
		AddHallController.setCinema(selectedCinema);
		Main.setRoot("AddHallPage");
	}

	@FXML
	private void editHall() {
		Hall selectedHall = hallTable.getSelectionModel().getSelectedItem();
		if (selectedHall != null) {
			EditHallController.setSelectedHall(selectedHall);
			Main.setRoot("EditHallPage");
		} else {
			showAlert("Select Hall", "Please select a hall to edit first.");
		}
	}

	@FXML
	private void deleteHall() {
		Hall selectedHall = hallTable.getSelectionModel().getSelectedItem();
		if (selectedHall != null) {
			hallDAO.deleteHall(selectedHall.getHallId());
			loadHalls();
		} else {
			showAlert("Select Hall", "Please select a hall to delete first. ");
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
	private void goBackToCinemaManagement() {
		Main.setRoot("CinemaManagementPage");
	}

	@FXML
	private void goToAdminHome() {
		Main.setRoot("AdminHomePage");
	}

}