package model;

public class Cinema {
    private int cinemaId;
    private String cinemaName;
    private String cinemaAddress;
    private String cinemaPhone;

    public Cinema() {
		
    }
    
    public Cinema(int cinemaId, String cinemaName, String cinemaAddress, String cinemaPhone) {
        this.cinemaId = cinemaId;
        this.cinemaName = cinemaName;
        this.cinemaAddress = cinemaAddress;
        this.cinemaPhone = cinemaPhone;
    }

    // Getters and Setters
    public int getCinemaId() { return cinemaId; }
    public String getCinemaName() { return cinemaName; }
    public String getCinemaAddress() { return cinemaAddress; }
    public String getCinemaPhone() { return cinemaPhone; }

    public void setCinemaId(int cinemaId) { this.cinemaId = cinemaId; }
    public void setCinemaName(String cinemaName) { this.cinemaName = cinemaName; }
    public void setCinemaAddress(String cinemaAddress) { this.cinemaAddress = cinemaAddress; }
    public void setCinemaPhone(String cinemaPhone) { this.cinemaPhone = cinemaPhone; }

    @Override
    public String toString() {
        return this.cinemaName; // 只显示影院名称
    }
	


}
