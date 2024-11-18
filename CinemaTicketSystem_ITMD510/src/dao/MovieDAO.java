package dao;

import model.DBConnection;
import model.Movie;
import model.MovieSchedule;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO {

    //获取所有电影信息
    public List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();
        String query = "SELECT movie_id, title, director, release_date, movie_description, duration FROM movie";
        
        try (Connection connection = DBConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
             
            while (rs.next()) {
                movies.add(new Movie(
                        rs.getInt("movie_id"),
                        rs.getString("title"),
                        rs.getString("director"),
                        rs.getDate("release_date").toLocalDate(),
                        rs.getString("movie_description"),
                        rs.getInt("duration")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    //根据关键字搜索电影信息
    public List<Movie> searchMovies(String keyword) {
        List<Movie> movies = new ArrayList<>();
        // 确保正确引用电影表和影院表的字段
        String query = "SELECT m.movie_id, m.title, m.director, m.release_date, m.movie_description, m.duration " +
                       "FROM movie m " +
                       "LEFT JOIN movie_schedule ms ON m.movie_id = ms.movie_id " +
                       "LEFT JOIN cinema c ON ms.hall_id = c.cinema_id " +
                       "WHERE m.title LIKE ? OR m.director LIKE ? OR c.cinema_name LIKE ?";
        
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
             
            pstmt.setString(1, "%" + keyword + "%");
            pstmt.setString(2, "%" + keyword + "%");
            pstmt.setString(3, "%" + keyword + "%");
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                movies.add(new Movie(
                        rs.getInt("movie_id"),
                        rs.getString("title"),
                        rs.getString("director"),
                        rs.getDate("release_date").toLocalDate(),
                        rs.getString("movie_description"),
                        rs.getInt("duration")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }
    
    public List<String> getActorsForMovie(int movieId) {
        List<String> actors = new ArrayList<>();
        String query = "SELECT a.actor_name FROM actor a " +
                       "JOIN movie_actor ma ON a.actor_id = ma.actor_id " +
                       "WHERE ma.movie_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, movieId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                actors.add(rs.getString("actor_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return actors;
    }

    public List<MovieSchedule> getSchedulesForMovie(int movieId) {
        List<MovieSchedule> schedules = new ArrayList<>();
        String query = "SELECT ms.schedule_id, ms.movie_id,c.cinema_name,h.hall_id, h.hall_name, h.hall_type, m.title, ms.start_time, ms.end_time, ms.ticket_price, " +
                "(SELECT COUNT(*) FROM seat_schedule ss WHERE ss.schedule_id = ms.schedule_id AND ss.is_seat_sold = false) AS remaining_seats " +
                "FROM movie_schedule ms " +
                "join movie m on ms.movie_id=m.movie_id "+
                "JOIN hall h ON ms.hall_id = h.hall_id " +
                "JOIN cinema c ON h.cinema_id = c.cinema_id " +
                "WHERE ms.movie_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setInt(1, movieId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                MovieSchedule schedule = new MovieSchedule(
                        rs.getInt("schedule_id"),
                        rs.getInt("movie_id"),
                        rs.getString("cinema_name"),
                        rs.getInt("hall_id"),
                        rs.getString("hall_name"),
                        rs.getString("hall_type"),
                        rs.getString("title"),
                        rs.getTimestamp("start_time").toLocalDateTime(),
                        rs.getTimestamp("end_time").toLocalDateTime(),
                        rs.getDouble("ticket_price"),
                        rs.getInt("remaining_seats")
                );
                schedules.add(schedule);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return schedules;
    }
    
    // 添加新的电影
    public boolean addMovie(Movie movie) {
        String query = "INSERT INTO movie (title, director, release_date, movie_description, duration) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
             
            pstmt.setString(1, movie.getTitle());
            pstmt.setString(2, movie.getDirector());
            pstmt.setDate(3, java.sql.Date.valueOf(movie.getReleaseDate()));
            pstmt.setString(4, movie.getDescription());
            pstmt.setInt(5, movie.getDuration());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //更新电影信息
    public boolean updateMovie(Movie movie) {
        String query = "UPDATE movie SET title = ?, director = ?, release_date = ?, movie_description = ?, duration = ? WHERE movie_id = ?";
        
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
             
            pstmt.setString(1, movie.getTitle());
            pstmt.setString(2, movie.getDirector());
            pstmt.setDate(3, java.sql.Date.valueOf(movie.getReleaseDate()));
            pstmt.setString(4, movie.getDescription());
            pstmt.setInt(5, movie.getDuration());
            pstmt.setInt(6, movie.getMovieId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

   // 删除电影
    public boolean deleteMovie(int movieId) {
        String query = "DELETE FROM movie WHERE movie_id = ?";
        
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
             
            pstmt.setInt(1, movieId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}