package controller;

import application.Main;
import dao.OrderDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.MovieSchedule;
import model.Seat;

import java.util.List;

public class OrderConfirmationController {

    @FXML
    private Label movieTitleLabel;
    @FXML
    private Label cinemaNameLabel;
    @FXML
    private Label hallNameLabel;
    @FXML
    private Label seatNumbersLabel;
    @FXML
    private Label ticketPriceLabel;
    @FXML
    private Label totalPriceLabel;
    @FXML
    private ComboBox<String> paymentMethodComboBox;

    private static List<Seat> selectedSeats;
    private static MovieSchedule selectedSchedule;
    private double totalPrice;

    public static void setSelectedSeats(List<Seat> seats) {
        selectedSeats = seats;
    }

    public static void setSelectedSchedule(MovieSchedule schedule) {
        selectedSchedule = schedule;
    }

    @FXML
    public void initialize() {
        paymentMethodComboBox.setItems(FXCollections.observableArrayList("Credit Card", "PayPal", "Apple Pay", "Google Pay", "Debit Card"));
        
        if (selectedSchedule != null) {
            movieTitleLabel.setText(selectedSchedule.getMovieTitle());
            cinemaNameLabel.setText(selectedSchedule.getCinemaName());
            hallNameLabel.setText(selectedSchedule.getHallName());
            ticketPriceLabel.setText(String.format("%.2f", selectedSchedule.getTicketPrice()));
            totalPrice = selectedSchedule.getTicketPrice() * selectedSeats.size();
            totalPriceLabel.setText(String.format("%.2f", totalPrice));
            
            StringBuilder seatNumbers = new StringBuilder();
            for (Seat seat : selectedSeats) {
                seatNumbers.append(seat.getSeatNumber()).append(" ");
            }
            seatNumbersLabel.setText(seatNumbers.toString().trim());
        }

    }

    @FXML
    private void processPayment() {
        String paymentMethod = paymentMethodComboBox.getValue();
        if (paymentMethod == null) {
            System.out.println("Please select a payment method! ");
            return;
        }

        OrderDAO orderDAO = new OrderDAO();
        int orderId = orderDAO.createOrder(Main.getCurrentUserId(), selectedSchedule, selectedSeats, totalPrice, paymentMethod);
        if (orderId != -1) {
            System.out.println("Payment successful! ");
            Main.setRoot("SystemHome");
        } else {
            System.out.println("Payment failed, please try again! ");
        }
    }

    @FXML
    private void goBackToSeatSelection() {
        Main.setRoot("SeatSelectionPage");
    }

    @FXML
    private void goToSystemHome() {
        Main.setRoot("SystemHome");
    }

    @FXML
    private void goToPersonalPage() {
        Main.setRoot("PersonalPage");
    }
}