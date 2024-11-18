package model;

public class Order {

	private int orderId;
	private int userId;
	private String movieTitle;
	private String cinemaName;
	private String hallName;
	private String seatNumbers;
	private String startTime;
	private String endTime; // 新增字段
	private int duration; // 新增字段
	private double totalPrice;
	private String paymentMethod;
	private String orderDate; // 新增字段



	public Order(int orderId, int userId, String movieTitle, String cinemaName, String hallName, String seatNumbers,
			String startTime, String endTime, int duration, double totalPrice, String paymentMethod, String orderDate) {
		super();
		this.orderId = orderId;
		this.userId = userId;
		this.movieTitle = movieTitle;
		this.cinemaName = cinemaName;
		this.hallName = hallName;
		this.seatNumbers = seatNumbers;
		this.startTime = startTime;
		this.endTime = endTime;
		this.duration = duration;
		this.totalPrice = totalPrice;
		this.paymentMethod = paymentMethod;
		this.orderDate = orderDate;
	}

	

	public int getUserId() {
		return userId;
	}



	public void setUserId(int userId) {
		this.userId = userId;
	}



	public String getEndTime() {
		return endTime;
	}



	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}



	public int getDuration() {
		return duration;
	}



	public void setDuration(int duration) {
		this.duration = duration;
	}



	public String getOrderDate() {
		return orderDate;
	}



	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}



	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getSeatNumbers() {
		return seatNumbers;
	}

	public void setSeatNumbers(String seatNumbers) {
		this.seatNumbers = seatNumbers;
	}

	// Getter and Setter methods
	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getMovieTitle() {
		return movieTitle;
	}

	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}

	public String getCinemaName() {
		return cinemaName;
	}

	public void setCinemaName(String cinemaName) {
		this.cinemaName = cinemaName;
	}

	public String getHallName() {
		return hallName;
	}

	public void setHallName(String hallName) {
		this.hallName = hallName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

}