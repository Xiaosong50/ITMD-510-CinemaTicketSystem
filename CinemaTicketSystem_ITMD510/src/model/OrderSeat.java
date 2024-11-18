package model;

public class OrderSeat {
    private int orderSeatId;
    private int orderId;
    private int seatScheduleId;

    public OrderSeat(int orderSeatId, int orderId, int seatScheduleId) {
        this.orderSeatId = orderSeatId;
        this.orderId = orderId;
        this.seatScheduleId = seatScheduleId;
    }

    // Getter and Setter methods
    public int getOrderSeatId() {
        return orderSeatId;
    }

    public void setOrderSeatId(int orderSeatId) {
        this.orderSeatId = orderSeatId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getSeatScheduleId() {
        return seatScheduleId;
    }

    public void setSeatScheduleId(int seatScheduleId) {
        this.seatScheduleId = seatScheduleId;
    }
}