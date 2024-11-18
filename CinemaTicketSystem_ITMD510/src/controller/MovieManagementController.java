package controller;

import application.Main;
import dao.MovieDAO;
//import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import model.Movie;

public class MovieManagementController {

	@FXML
	private TableView<Movie> movieTable;
	@FXML
	private TableColumn<Movie, Integer> movieIdColumn;
	@FXML
	private TableColumn<Movie, String> titleColumn;
	@FXML
	private TableColumn<Movie, String> directorColumn;
	@FXML
	private TableColumn<Movie, String> releaseDateColumn;
	@FXML
	private TableColumn<Movie, Integer> durationColumn;

	private MovieDAO movieDAO;
	private ObservableList<Movie> movieList;

	public MovieManagementController() {
		movieDAO = new MovieDAO();
		movieList = FXCollections.observableArrayList();
	}

	@FXML
	public void initialize() {
		movieIdColumn.setCellValueFactory(new PropertyValueFactory<>("movieId"));
		titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		directorColumn.setCellValueFactory(new PropertyValueFactory<>("director"));
		releaseDateColumn.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
		durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));

		loadMovies();
	}

	private void loadMovies() {
		movieList.setAll(movieDAO.getAllMovies());
		movieTable.setItems(movieList);
	}

	@FXML
	private void addMovie() {
		Main.setRoot("MovieAdd");
	}

	@FXML
	private void editMovie() {
		Movie selectedMovie = movieTable.getSelectionModel().getSelectedItem();
		if (selectedMovie != null) {
			MovieEditController.setSelectedMovie(selectedMovie);
			Main.setRoot("MovieEditPage");
		} else {
			showAlert("Please select a movie to edit!");
		}
	}

	@FXML
	private void deleteMovie() {
		Movie selectedMovie = movieTable.getSelectionModel().getSelectedItem();
		if (selectedMovie != null) {
			movieDAO.deleteMovie(selectedMovie.getMovieId());
			loadMovies();
		} else {
			showAlert("Please select a movie to delete! ");
		}
	}

	@FXML
	private void manageSchedules() {
		Movie selectedMovie = movieTable.getSelectionModel().getSelectedItem();
		if (selectedMovie != null) {
			MovieScheduleManagementController.setMovie(selectedMovie);
			Main.setRoot("MovieScheduleManagement"); 
		} else {
			showAlert("Please select a movie to manage schedule! ");
		}
	}

	private void showAlert(String message) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setContentText(message);
		alert.show();
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