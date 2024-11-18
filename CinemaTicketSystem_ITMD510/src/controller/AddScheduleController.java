package controller;

import application.Main;
import dao.CinemaDAO;
import dao.HallDAO;
import dao.MovieScheduleDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Cinema;
import model.Hall;
import model.Movie;
import model.MovieSchedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AddScheduleController {

    @FXML
    private ComboBox<Cinema> cinemaComboBox;
    @FXML
    private TextField cinemaIdField;
    @FXML
    private ComboBox<Hall> hallComboBox;
    @FXML
    private TextField hallIdField;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private ComboBox<Integer> hourComboBox;
    @FXML
    private ComboBox<Integer> minuteComboBox;
    @FXML
    private TextField endTimeField;
    @FXML
    private TextField ticketPriceField;

    private final CinemaDAO cinemaDAO = new CinemaDAO();
    private final HallDAO hallDAO = new HallDAO();
    private final MovieScheduleDAO movieScheduleDAO = new MovieScheduleDAO();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static Movie selectedMovie;

    public static void setSelectedMovie(Movie movie) {
        selectedMovie = movie;
    }

    @FXML
    public void initialize() {
        loadCinemas();
        initializeTimeSelectors();

        // 监听日期和时间选择以自动计算结束时间
        startDatePicker.valueProperty().addListener((obs, oldDate, newDate) -> calculateEndTime());
        hourComboBox.valueProperty().addListener((obs, oldHour, newHour) -> calculateEndTime());
        minuteComboBox.valueProperty().addListener((obs, oldMinute, newMinute) -> calculateEndTime());
    }

    private void initializeTimeSelectors() {
        hourComboBox.setItems(FXCollections.observableArrayList(IntStream.rangeClosed(0, 23).boxed().collect(Collectors.toList())));
        minuteComboBox.setItems(FXCollections.observableArrayList(IntStream.rangeClosed(0, 59).boxed().collect(Collectors.toList())));
 
    }
    private void loadCinemas() {
        List<Cinema> cinemas = cinemaDAO.getAllCinemas();
        cinemaComboBox.setItems(FXCollections.observableArrayList(cinemas));
    }

    @FXML
    private void loadHallsForSelectedCinema() {
        Cinema selectedCinema = cinemaComboBox.getValue();
        if (selectedCinema != null) {
            cinemaIdField.setText(String.valueOf(selectedCinema.getCinemaId()));
            List<Hall> halls = hallDAO.getHallsByCinemaId(selectedCinema.getCinemaId());
            hallComboBox.setItems(FXCollections.observableArrayList(halls));
        }
    }

    @FXML
    private void updateHallIdField() {
        Hall selectedHall = hallComboBox.getValue();
        if (selectedHall != null) {
            hallIdField.setText(String.valueOf(selectedHall.getHallId()));
        }
    }

    private void calculateEndTime() {
        LocalDate date = startDatePicker.getValue();
        Integer hour = hourComboBox.getValue();
        Integer minute = minuteComboBox.getValue();

        if (date != null && hour != null && minute != null) {
            LocalDateTime startTime = LocalDateTime.of(date, LocalTime.of(hour, minute));
            int duration = selectedMovie.getDuration();
            LocalDateTime endTime = startTime.plusMinutes(duration);
            endTimeField.setText(endTime.format(formatter));
        }
    }

    @FXML
    private void addSchedule() {
        try {
            int hallId = Integer.parseInt(hallIdField.getText());
            LocalDate date = startDatePicker.getValue();
            int hour = hourComboBox.getValue();
            int minute = minuteComboBox.getValue();
            double ticketPrice = Double.parseDouble(ticketPriceField.getText().trim());

            if (date == null || hourComboBox.getValue() == null || minuteComboBox.getValue() == null) {
                showAlert("Input Error", "Please make sure you select a date and time.");
                return;
            }

            LocalDateTime startTime = LocalDateTime.of(date, LocalTime.of(hour, minute));
            LocalDateTime endTime = startTime.plusMinutes(selectedMovie.getDuration());

            if (!movieScheduleDAO.isScheduleTimeValid(hallId, startTime, endTime)) {
                showAlert("Time Conflict", "The selected theater has movies scheduled during this time period or the time interval is less than 10 minutes.");
                return;
            }

            MovieSchedule schedule = new MovieSchedule(0, selectedMovie.getMovieId(),
                    cinemaComboBox.getValue().getCinemaName(), hallId, hallComboBox.getValue().getHallName(),
                    hallComboBox.getValue().getHallType(), selectedMovie.getTitle(), startTime, endTime, ticketPrice, 0);
            movieScheduleDAO.addSchedule(schedule);

            showAlert("Success", "Movie schedule add succeed！");
            goBackToScheduleManagement();
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please make sure the price is a number.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while adding movie schedule, please try again later.");
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
    private void goBackToScheduleManagement() {
        Main.setRoot("MovieScheduleManagement");
    }
}