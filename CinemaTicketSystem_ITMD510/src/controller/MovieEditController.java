package controller;

import application.Main;
import dao.MovieDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Movie;

import java.time.LocalDate;

public class MovieEditController {

    @FXML
    private TextField titleField;
    @FXML
    private TextField directorField;
    @FXML
    private DatePicker releaseDatePicker;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private TextField durationField;

    private static Movie selectedMovie;
    private MovieDAO movieDAO;

    public MovieEditController() {
        movieDAO = new MovieDAO();
    }

    public static void setSelectedMovie(Movie movie) {
        selectedMovie = movie;
    }

    @FXML
    public void initialize() {
        if (selectedMovie != null) {
            loadMovieDetails();
        }
    }

    private void loadMovieDetails() {
        titleField.setText(selectedMovie.getTitle());
        directorField.setText(selectedMovie.getDirector());
        releaseDatePicker.setValue(selectedMovie.getReleaseDate());
        descriptionArea.setText(selectedMovie.getDescription());
        durationField.setText(String.valueOf(selectedMovie.getDuration()));
    }

    @FXML
    private void updateMovie() {
        String title = titleField.getText();
        String director = directorField.getText();
        LocalDate releaseDate = releaseDatePicker.getValue();
        String description = descriptionArea.getText();
        int duration;

        try {
            duration = Integer.parseInt(durationField.getText());
        } catch (NumberFormatException e) {
            showAlert("Duration Format Error", "Please enter the correct duration (minutes). ");
            return;
        }

        if (title.isEmpty() || director.isEmpty() || releaseDate == null || description.isEmpty()) {
            showAlert("Input Error", "Please fill in all fields. ");
            return;
        }

        selectedMovie.setTitle(title);
        selectedMovie.setDirector(director);
        selectedMovie.setReleaseDate(releaseDate);
        selectedMovie.setDescription(description);
        selectedMovie.setDuration(duration);

        boolean success = movieDAO.updateMovie(selectedMovie);

        if (success) {
            showAlert("Success", "Movie information has been updated. ");
            Main.setRoot("MovieManagementPage");
        } else {
            showAlert("Error", "Failed to update movie information, please try again. ");
        }
    }

    @FXML
    private void goToAdminHome() {
        Main.setRoot("AdminHomePage");
    }

    @FXML
    private void goToMovieManagement() {
        Main.setRoot("MovieManagementPage");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}