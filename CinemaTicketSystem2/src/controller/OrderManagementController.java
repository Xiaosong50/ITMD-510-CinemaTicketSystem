package controller;

import application.Main;
import dao.OrderDAO;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Order;

public class OrderManagementController {

    @FXML
    private TableView<Order> orderTable;
    @FXML
    private TableColumn<Order, Integer> orderIdColumn;
    @FXML
    private TableColumn<Order, Integer> userIdColumn;
    @FXML
    private TableColumn<Order, String> movieTitleColumn;
    @FXML
    private TableColumn<Order, Integer> durationColumn;
    @FXML
    private TableColumn<Order, String> cinemaNameColumn;
    @FXML
    private TableColumn<Order, String> hallNameColumn;
    @FXML
    private TableColumn<Order, String> seatNumbersColumn;
    @FXML
    private TableColumn<Order, String> startTimeColumn;
    @FXML
    private TableColumn<Order, String> endTimeColumn;
    @FXML
    private TableColumn<Order, Double> totalPriceColumn;
    @FXML
    private TableColumn<Order, String> paymentMethodColumn;
    @FXML
    private TableColumn<Order, String> orderDateColumn;

    private OrderDAO orderDAO;
    private ObservableList<Order> orderList;

    public OrderManagementController() {
        orderDAO = new OrderDAO();
        orderList = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize() {
        loadOrderData();

        orderIdColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getOrderId()));
        userIdColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getUserId()));
        movieTitleColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getMovieTitle()));
        durationColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getDuration()));
        cinemaNameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCinemaName()));
        hallNameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getHallName()));
        seatNumbersColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getSeatNumbers()));
        startTimeColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getStartTime()));
        endTimeColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getEndTime()));
        totalPriceColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getTotalPrice()));
        paymentMethodColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getPaymentMethod()));
        orderDateColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getOrderDate()));
    }

    private void loadOrderData() {
        orderList.setAll(orderDAO.getAllOrders());
        orderTable.setItems(orderList);
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