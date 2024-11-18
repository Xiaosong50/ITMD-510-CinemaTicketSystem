package controller;

import application.Main;
import dao.UserDAO;
import dao.OrderDAO;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Order;
import model.User;

public class PersonalPageController {

    @FXML
    private TextField userNameField;
    @FXML
    private PasswordField userPasswordField;
    @FXML
    private TextField userGenderField;
    @FXML
    private TextField userPhoneField;
    @FXML
    private TextField userEmailField;

    @FXML
    private TableView<Order> orderTable;
    @FXML
    private TableColumn<Order, Integer> orderIdColumn;
    @FXML
    private TableColumn<Order, String> movieTitleColumn;
    @FXML
    private TableColumn<Order, String> cinemaNameColumn;
    @FXML
    private TableColumn<Order, String> hallNameColumn;
    @FXML
    private TableColumn<Order, String> seatNumbersColumn;
    @FXML
    private TableColumn<Order, String> startTimeColumn;
    @FXML
    private TableColumn<Order, Double> totalPriceColumn;
    @FXML
    private TableColumn<Order, Integer> durationColumn;
    @FXML
    private TableColumn<Order, String> endTimeColumn;
    @FXML
    private TableColumn<Order, String> orderDateColumn;
    @FXML
    private TableColumn<Order, String> paymentMethodColumn;
   

    private UserDAO userDAO;
    private OrderDAO orderDAO;
    private ObservableList<Order> orderList;

    public PersonalPageController() {
        userDAO = new UserDAO();
        orderDAO = new OrderDAO();
        orderList = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize() {
        loadUserInfo();
        loadOrderHistory();

        orderIdColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getOrderId()));
        movieTitleColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getMovieTitle()));
        cinemaNameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCinemaName()));
        hallNameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getHallName()));
        seatNumbersColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getSeatNumbers()));
        startTimeColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getStartTime()));
        endTimeColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getEndTime()));
        durationColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getDuration()));
        totalPriceColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getTotalPrice()));
        paymentMethodColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getPaymentMethod()));
        orderDateColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getOrderDate()));

    }
    
    private void loadUserInfo() {
        User user = userDAO.getUser(Main.getCurrentUserId());
        if (user != null) {
            userNameField.setText(user.getUserName());
            userPasswordField.setText(user.getUserPassword());
            userGenderField.setText(user.getUserGender());
            userPhoneField.setText(user.getUserPhone());
            userEmailField.setText(user.getUserEmail());
        }
    }

    private void loadOrderHistory() {
        orderList.setAll(orderDAO.getOrdersByUserId(Main.getCurrentUserId()));
        orderTable.setItems(orderList);
    }

    @FXML
    private void updateUserInfo() {
        String userName = userNameField.getText();
        String password = userPasswordField.getText();
        String gender = userGenderField.getText();
        String phone = userPhoneField.getText();
        String email = userEmailField.getText();
        
        User user = new User(Main.getCurrentUserId(), userName, password, gender, phone, email);
        userDAO.updateUser(user);
    }

    @FXML
    private void logOut() {
        Main.setRoot("Login");
    }

    @FXML
    private void goToSystemHome() {
        Main.setRoot("SystemHome");
    }

    @FXML
    private void goToUserProfilePage() {
        Main.setRoot("PersonalPage");
    }
}