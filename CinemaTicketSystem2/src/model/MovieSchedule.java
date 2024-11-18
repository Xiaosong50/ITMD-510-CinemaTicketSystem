package model;

import java.time.LocalDateTime;

public class MovieSchedule {
    private int scheduleId;
    private int movieId;
    private String cinemaName;
    private int hallId;
    private String hallName;
    private String hallType;
    private String movieTitle;  // 新增字段，表示电影标题
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double ticketPrice;
    private int remainingSeats;

    

    

	public MovieSchedule(int scheduleId, int movieId, String cinemaName, int hallId, String hallName, String hallType,
			String movieTitle, LocalDateTime startTime, LocalDateTime endTime, double ticketPrice, int remainingSeats) {
		this.scheduleId = scheduleId;
		this.movieId = movieId;
		this.cinemaName = cinemaName;
		this.hallId = hallId;
		this.hallName = hallName;
		this.hallType = hallType;
		this.movieTitle = movieTitle;
		this.startTime = startTime;
		this.endTime = endTime;
		this.ticketPrice = ticketPrice;
		this.remainingSeats = remainingSeats;
	}



	public int getHallId() {
		return hallId;
	}



	public void setHallId(int hallId) {
		this.hallId = hallId;
	}



	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
	}



	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}



	public void setCinemaName(String cinemaName) {
		this.cinemaName = cinemaName;
	}



	public void setHallName(String hallName) {
		this.hallName = hallName;
	}



	public void setHallType(String hallType) {
		this.hallType = hallType;
	}



	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}



	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}



	public void setTicketPrice(double ticketPrice) {
		this.ticketPrice = ticketPrice;
	}



	public void setRemainingSeats(int remainingSeats) {
		this.remainingSeats = remainingSeats;
	}



	// Getter and Setter methods
    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public String getHallName() {
        return hallName;
    }

    public String getHallType() {
        return hallType;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public int getRemainingSeats() {
        return remainingSeats;
    }
	
}