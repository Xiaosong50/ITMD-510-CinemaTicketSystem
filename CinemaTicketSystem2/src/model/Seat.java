package model;

public class Seat {
    private int seatId;
    private String seatNumber;
    private boolean isSold;
    private boolean selected; // 新增字段，用于表示座位是否被选中

    public Seat(int seatId, String seatNumber, boolean isSold) {
        this.seatId = seatId;
        this.seatNumber = seatNumber;
        this.isSold = isSold;
        this.selected = false; // 初始化为未选中状态
    }

    // Getter and Setter methods
    public int getSeatId() {
        return seatId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public boolean isSold() {
        return isSold;
    }

    public void setSeatId(int seatId) {
		this.seatId = seatId;
	}

	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	public void setSold(boolean sold) {
        isSold = sold;
    }

    // 新增的 isSelected 和 setSelected 方法
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}