package controller;

import application.Main;
import dao.SeatDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.MovieSchedule;
import model.Seat;

import java.util.List;

public class SeatSelectionController {

    @FXML
    private TilePane seatLayout;

    private SeatDAO seatDAO;
    private static MovieSchedule selectedSchedule; 
    private ObservableList<Seat> selectedSeats;    

    public SeatSelectionController() {
        seatDAO = new SeatDAO();
        selectedSeats = FXCollections.observableArrayList();
    }

    public static void setSelectedSchedule(MovieSchedule schedule) {
        selectedSchedule = schedule;
    }

    @FXML
    public void initialize() {
        if (selectedSchedule != null) {
            loadSeatLayout(selectedSchedule.getScheduleId()); // 使用选中的 `scheduleId` 加载座位布局
        	seatLayout.setPrefColumns(10); // 设置每排 10 个座位
        }
    }

    private void loadSeatLayout(int scheduleId) {
        List<Seat> seats = seatDAO.getSeatsForSchedule(scheduleId);
        seatLayout.getChildren().clear();

        for (Seat seat : seats) {
            Rectangle seatRectangle = new Rectangle(30, 30);
            seatRectangle.setUserData(seat);

            if (seat.isSold()) {
                seatRectangle.setFill(Color.RED); // 已售座位显示为红色
            } else {
                seatRectangle.setFill(Color.GREEN); // 可选座位显示为绿色
                seatRectangle.setOnMouseClicked(e -> toggleSeatSelection(seatRectangle));
            }
            seatLayout.getChildren().add(seatRectangle);
        }
    }

    private void toggleSeatSelection(Rectangle seatRectangle) {
        Seat seat = (Seat) seatRectangle.getUserData();

        if (seat.isSelected()) {
            seat.setSelected(false);
            seatRectangle.setFill(Color.GREEN);
            selectedSeats.remove(seat); // 从已选座位列表中移除
        } else {
            seat.setSelected(true);
            seatRectangle.setFill(Color.BLUE);
            selectedSeats.add(seat); // 添加到已选座位列表
        }
    }

    @FXML
    private void proceedToOrderConfirmation() {
        if (selectedSeats.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("No seat selected. ");
            alert.setContentText("Please choose your seat first! ");
            alert.showAndWait();
            return;
        }
        OrderConfirmationController.setSelectedSchedule(selectedSchedule);
        OrderConfirmationController.setSelectedSeats(selectedSeats);

        Main.setRoot("OrderConfirmationPage");
    }

    @FXML
    private void goBackToMovieDetail() {
        Main.setRoot("MovieDetailPage");
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