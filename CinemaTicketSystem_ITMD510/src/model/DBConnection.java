package model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/fp510";
    private static final String USER = "root";
    private static final String PASSWORD = "Lxs,123321";
    
//    private static final String URL = "jdbc:mysql://www.papademas.net:3307/510fp?autoReconnect=true&useSSL=false";
//    private static final String USER = "fp510";
//    private static final String PASSWORD = "510";


    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
