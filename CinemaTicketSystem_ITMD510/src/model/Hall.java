package model;

public class Hall {
    private int hallId;
    private int cinemaId;
    private String hallName;
    private String hallType;
    private int seatCount;

    public Hall(int hallId, String hallName, String hallType) {
        this.hallId = hallId;
        this.hallName = hallName;
        this.hallType = hallType;
    }
    
    

    public Hall() {

	}
    
    public Hall(int hallId, int cinemaId, String hallName, String hallType, int seatCount) {
		super();
		this.hallId = hallId;
		this.cinemaId = cinemaId;
		this.hallName = hallName;
		this.hallType = hallType;
		this.seatCount = seatCount;
	}


	public int getCinemaId() {
		return cinemaId;
	}



	public void setCinemaId(int cinemaId) {
		this.cinemaId = cinemaId;
	}



	public int getSeatCount() {
		return seatCount;
	}



	public void setSeatCount(int seatCount) {
		this.seatCount = seatCount;
	}



	public void setHallId(int hallId) {
		this.hallId = hallId;
	}



	public void setHallName(String hallName) {
		this.hallName = hallName;
	}



	public void setHallType(String hallType) {
		this.hallType = hallType;
	}


	public int getHallId() {
        return hallId;
    }

    public String getHallName() {
        return hallName;
    }

    public String getHallType() {
        return hallType;
    }

    @Override
    public String toString() {
        return hallName;
    }
}