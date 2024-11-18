package controller;

import java.util.List;

import application.Main;
import dao.MovieDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Movie;
import model.MovieSchedule;

public class MovieDetailController {

    @FXML
    private Label titleLabel;
    @FXML
    private Label directorLabel;
    @FXML
    private Label actorsLabel;
    @FXML
    private Label releaseDateLabel;
    @FXML
    private Label descriptionLabel;
    
    @FXML
    private TableView<MovieSchedule> scheduleTable;
    @FXML
    private TableColumn<MovieSchedule, String> cinemaColumn;
    @FXML
    private TableColumn<MovieSchedule, String> hallColumn;
    @FXML
    private TableColumn<MovieSchedule, String> hallTypeColumn;
    @FXML
    private TableColumn<MovieSchedule, String> startTimeColumn;
    @FXML
    private TableColumn<MovieSchedule, Integer> remainingSeatsColumn;
    
    private MovieDAO movieDAO;
    private static Movie selectedMovie; 
    private ObservableList<MovieSchedule> scheduleList;
    
    public static void setSelectedMovie(Movie movie) {
        selectedMovie = movie;
    }
    
    public MovieDetailController() {
        movieDAO = new MovieDAO();
        scheduleList = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize() {
    	
        cinemaColumn.setCellValueFactory(new PropertyValueFactory<>("cinemaName"));
        hallColumn.setCellValueFactory(new PropertyValueFactory<>("hallName"));
        hallTypeColumn.setCellValueFactory(new PropertyValueFactory<>("hallType"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        remainingSeatsColumn.setCellValueFactory(new PropertyValueFactory<>("remainingSeats"));
        
     // 加载电影详情和排片信息
        if (selectedMovie != null) {
            loadMovieDetails();
            loadMovieSchedules();
        }
    }

    private void loadMovieDetails() {
        titleLabel.setText(selectedMovie.getTitle());
        directorLabel.setText("Director: " + selectedMovie.getDirector());
        actorsLabel.setText("aActor: " + movieDAO.getActorsForMovie(selectedMovie.getMovieId()));
        releaseDateLabel.setText("Release date: " + selectedMovie.getReleaseDate());
        descriptionLabel.setText("Movie introduction: " + selectedMovie.getDescription());
        System.out.println("Loaded movie details for: " + selectedMovie.getTitle());     
    }

    private void loadMovieSchedules() {
        List<MovieSchedule> schedules = movieDAO.getSchedulesForMovie(selectedMovie.getMovieId());
        scheduleList.setAll(schedules);
        scheduleTable.setItems(scheduleList);
        System.out.println("Loaded " + schedules.size() + " schedule(s) for movie.");
    }

    @FXML
    private void viewRemainingSeats() {
    	MovieSchedule selectedSchedule = scheduleTable.getSelectionModel().getSelectedItem();
        if (selectedSchedule != null) {
            SeatSelectionController.setSelectedSchedule(selectedSchedule);
            Main.setRoot("SeatSelectionPage");
            System.out.println("Navigating to seat selection for schedule ID: " + selectedSchedule.getScheduleId());
        } else {
            System.out.println("Please select a movie schedule first！");
        }
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