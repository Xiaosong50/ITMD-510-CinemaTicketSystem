package application;

import model.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static Stage stage;
    private static int currentUserId;  
    private static int currentAdminId;  

    public static int getCurrentAdminId() {
		return currentAdminId;
	}

	public static void setCurrentAdminId(int currentAdminId) {
		Main.currentAdminId = currentAdminId;
	}

	@Override
    public void start(Stage primaryStage) {
        Locale.setDefault(Locale.ENGLISH);

        try {
            Connection conn = DBConnection.getConnection();
            if (conn != null) {
                System.out.println("Database connection successful!");
            } else {
                System.out.println("Database connection failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
            Scene scene = new Scene(root);
            stage = primaryStage;
            primaryStage.setTitle("Cinema Ticket System");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setRoot(String fxml) {
        try {
            Parent root = FXMLLoader.load(Main.class.getResource("/view/" + fxml + ".fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getCurrentUserId() {
        return currentUserId;
    }

    public static void setCurrentUserId(int userId) {
        currentUserId = userId;
    }

    public static void main(String[] args) {
        launch(args);
    }
}