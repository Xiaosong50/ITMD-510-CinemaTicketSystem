package controller;

import application.Main;
import dao.AdminDAO;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Admin;

public class AdminManagementController {

	@FXML
	private TableView<Admin> adminTable;
	@FXML
	private TableColumn<Admin, Integer> adminIdColumn;
	@FXML
	private TableColumn<Admin, String> adminNameColumn;
	@FXML
	private TableColumn<Admin, String> adminPhoneColumn;
	@FXML
	private TableColumn<Admin, String> adminPositionColumn;

	@FXML
	private TextField adminNameField;
	@FXML
	private TextField adminPhoneField;
	@FXML
	private TextField adminPositionField;
	@FXML
	private PasswordField adminPasswordField;

	private AdminDAO adminDAO;
	private ObservableList<Admin> adminList;

	public AdminManagementController() {
		adminDAO = new AdminDAO();
		adminList = FXCollections.observableArrayList();
	}

	@FXML
	public void initialize() {

		loadAdminData();

		adminIdColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getAdminId()));
		adminNameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getAdminName()));
		adminPhoneColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getAdminPhone()));
		adminPositionColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getAdminPosition()));

	}

	private void loadAdminData() {
		int managerId = Main.getCurrentAdminId();
		adminList.setAll(adminDAO.getAdminsByManager(managerId));
		adminTable.setItems(adminList);
	}

	@FXML
	private void addAdmin() {
		String name = adminNameField.getText();
		String phone = adminPhoneField.getText();
		String position = adminPositionField.getText();
		String password = adminPasswordField.getText();

		if (name.isEmpty() || phone.isEmpty() || position == null || password.isEmpty()) {
			showAlert("Input Error", "Please fill in all fields！");
			return;
		}

		Admin newAdmin = new Admin(0, name, password, phone, position);
		adminDAO.addAdmin(newAdmin, Main.getCurrentAdminId());

		adminList.add(newAdmin);
		clearFields();
	}

	@FXML
	private void editAdminPosition() {
		Admin selectedAdmin = adminTable.getSelectionModel().getSelectedItem();
		if (selectedAdmin != null) {
			String newPosition = adminPositionField.getText();
			selectedAdmin.setAdminPosition(newPosition);
			adminDAO.updateAdminPosition(selectedAdmin);
			adminTable.refresh();
		} else {
			showAlert("Select Administrator", "Please select an administrator to edit first.");
		}
	}

	@FXML
	private void deleteAdmin() {
		Admin selectedAdmin = adminTable.getSelectionModel().getSelectedItem();
		if (selectedAdmin != null) {
			adminDAO.deleteAdmin(selectedAdmin.getAdminId());
			adminList.remove(selectedAdmin);
		} else {
			showAlert("Select Administrator", "Please select an administrator to delete first.");
		}
	}

	private void clearFields() {
		adminNameField.clear();
		adminPhoneField.clear();
		adminPasswordField.clear();
		adminPositionField.clear();
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