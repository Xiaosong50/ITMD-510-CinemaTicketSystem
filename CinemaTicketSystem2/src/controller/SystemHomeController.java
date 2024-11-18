package controller;

import application.Main;
import dao.MovieDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import model.Movie;

public class SystemHomeController {

    @FXML
    private TextField searchField;
    @FXML
    private TableView<Movie> movieTable;
    @FXML
    private TableColumn<Movie, String> titleColumn;
    @FXML
    private TableColumn<Movie, String> directorColumn;
    @FXML
    private TableColumn<Movie, String> releaseDateColumn;

    private MovieDAO movieDAO;
    private ObservableList<Movie> movieList;

    public SystemHomeController() {
        movieDAO = new MovieDAO();
        movieList = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        directorColumn.setCellValueFactory(new PropertyValueFactory<>("director"));
        releaseDateColumn.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        loadMovies();
    }

    @FXML
    public void handleSearch() {
        String keyword = searchField.getText();
        if (keyword.isEmpty()) {
            loadMovies();
        } else {
            movieList.setAll(movieDAO.searchMovies(keyword));
        }
        movieTable.setItems(movieList);
    }

    private void loadMovies() {
        movieList.setAll(movieDAO.getAllMovies());
        movieTable.setItems(movieList);
    }
    
    

    @FXML
    private void handleMovieTableClick(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
            Movie selectedMovie = movieTable.getSelectionModel().getSelectedItem();
            if (selectedMovie != null) {
                openMovieDetailPage(selectedMovie);
            }
        }
    }

    private void openMovieDetailPage(Movie movie) {
        if (movie != null) {
            System.out.println("Selected movie: " + movie.getTitle());
            MovieDetailController.setSelectedMovie(movie);
        } else {
            System.out.println("Selected movie is null");
        }
        
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