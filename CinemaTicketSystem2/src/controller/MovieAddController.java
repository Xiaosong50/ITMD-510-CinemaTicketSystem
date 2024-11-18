package controller;

import application.Main;
import dao.MovieDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Movie;

import java.time.LocalDate;

public class MovieAddController {

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

    private MovieDAO movieDAO;

    public MovieAddController() {
        movieDAO = new MovieDAO();
    }

    @FXML
    private void saveMovie() {
        String title = titleField.getText();
        String director = directorField.getText();
        LocalDate releaseDate = releaseDatePicker.getValue();
        String description = descriptionArea.getText();
        int duration;

        try {
            duration = Integer.parseInt(durationField.getText());
        } catch (NumberFormatException e) {
            showAlert("Duration Format Error", "Please enter the correct duration (minutes) ");
            return;
        }

        if (title.isEmpty() || director.isEmpty() || releaseDate == null || description.isEmpty()) {
            showAlert("Input Error", "Please fill in all fields! ");
            return;
        }

        Movie newMovie = new Movie(0, title, director, releaseDate, description, duration);
        boolean success = movieDAO.addMovie(newMovie);

        if (success) {
            showAlert("Success", "Movie added. ");
            Main.setRoot("MovieManagementPage");
        } else {
            showAlert("Error", "Failed to add movie, please try again. ");
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