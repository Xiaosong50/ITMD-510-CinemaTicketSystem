package model;

import java.time.LocalDate;

public class Movie {
    private int movieId;
    private String title;
    private String director;
    private LocalDate releaseDate;
    private String description;
    private int duration;

    public Movie(int movieId, String title, String director, LocalDate releaseDate, String description, int duration) {
        this.movieId = movieId;
        this.title = title;
        this.director = director;
        this.releaseDate = releaseDate;
        this.description = description;
        this.duration = duration;
    }

    public Movie() {
		
	}

	public int getMovieId() { return movieId; }
    public String getTitle() { return title; }
    public String getDirector() { return director; }
    public LocalDate getReleaseDate() { return releaseDate; }
    public String getDescription() { return description; }
    public int getDuration() { return duration; }

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
}