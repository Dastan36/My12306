package com.neusoft.my12306.domain;

import android.content.Intent;

public class Seat {

    private  String seatName;
    private Integer seatNum;
    private Double seatPrice;

    public Seat() {
    }

    public Seat(String seatName, Integer seatNum, Double seatPrice) {
        this.seatName = seatName;
        this.seatNum = seatNum;
        this.seatPrice = seatPrice;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    public Integer getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(Integer seatNum) {
        this.seatNum = seatNum;
    }

    public Double getSeatPrice() {
        return seatPrice;
    }

    public void setSeatPrice(Double seatPrice) {
        this.seatPrice = seatPrice;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "seatName='" + seatName + '\'' +
                ", seatNum=" + seatNum +
                ", seatPrice=" + seatPrice +
                '}';
    }
}
