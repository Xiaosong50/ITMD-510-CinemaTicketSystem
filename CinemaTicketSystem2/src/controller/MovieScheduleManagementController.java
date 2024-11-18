package controller;

import application.Main;
import dao.MovieDAO;
import dao.MovieScheduleDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Movie;
import model.MovieSchedule;

import java.util.List;

public class MovieScheduleManagementController {

    @FXML
    private TableView<MovieSchedule> scheduleTable;
    @FXML
    private TableColumn<MovieSchedule, Integer> scheduleIdColumn;
    @FXML
    private TableColumn<MovieSchedule, String> movieTitleColumn;
    @FXML
    private TableColumn<MovieSchedule, String> cinemaNameColumn;
    @FXML
    private TableColumn<MovieSchedule, String> hallNameColumn;
    @FXML
    private TableColumn<MovieSchedule, String> hallTypeColumn;
    @FXML
    private TableColumn<MovieSchedule, String> startTimeColumn;
    @FXML
    private TableColumn<MovieSchedule, String> endTimeColumn;
    @FXML
    private TableColumn<MovieSchedule, Double> ticketPriceColumn;
    @FXML
    private TableColumn<MovieSchedule, Integer> remainingSeatsColumn;

    private final MovieScheduleDAO movieScheduleDAO = new MovieScheduleDAO();
    private final MovieDAO movieDAO = new MovieDAO();
    private static Movie movie = new Movie();
    
    public static Movie getMovie() {
		return movie;
	}
	public static void setMovie(Movie selectedMovie) {
    	movie=selectedMovie;
    }
    @FXML
    public void initialize() {
        scheduleIdColumn.setCellValueFactory(new PropertyValueFactory<>("scheduleId"));
        movieTitleColumn.setCellValueFactory(new PropertyValueFactory<>("movieTitle"));
        cinemaNameColumn.setCellValueFactory(new PropertyValueFactory<>("cinemaName"));
        hallNameColumn.setCellValueFactory(new PropertyValueFactory<>("hallName"));
        hallTypeColumn.setCellValueFactory(new PropertyValueFactory<>("hallType"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        ticketPriceColumn.setCellValueFactory(new PropertyValueFactory<>("ticketPrice"));
        remainingSeatsColumn.setCellValueFactory(new PropertyValueFactory<>("remainingSeats"));

        loadSchedules();
    }

    private void loadSchedules() {
        List<MovieSchedule> schedules = movieDAO.getSchedulesForMovie(movie.getMovieId());
        scheduleTable.getItems().setAll(schedules);
    }

    @FXML
    private void addSchedule() {
    	AddScheduleController.setSelectedMovie(movie);
        Main.setRoot("AddSchedulePage");
    }

    @FXML
    private void editSchedule() {
        MovieSchedule selectedSchedule = scheduleTable.getSelectionModel().getSelectedItem();
        if (selectedSchedule != null) {
            EditScheduleController.setSelectedSchedule(selectedSchedule);
            EditScheduleController.setSelectedMovie(movie);

            Main.setRoot("EditSchedulePage");
        } else {
            showAlert("Select Movie Schedule", "Please select a movie schedule to edit first. ");
        }
    }

    @FXML
    private void deleteSchedule() {
        MovieSchedule selectedSchedule = scheduleTable.getSelectionModel().getSelectedItem();
        if (selectedSchedule != null) {
            movieScheduleDAO.deleteSchedule(selectedSchedule.getScheduleId());
            loadSchedules();
        } else {
            showAlert("Select Movie Schedule", "Please select a movie schedule to edit first. ");
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
    private void goToMovieManagement() {
        Main.setRoot("MovieManagementPage");
    }

    @FXML
    private void goToAdminProfile() {
        Main.setRoot("AdminProfilePage");
    }
}