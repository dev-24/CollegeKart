package com.example.dardev.collegekart.model;

import java.util.Date;

/**
 * Created by Dev on 04-03-2016.
 */
public class Transaction {
    private String title;
    private int imageId;
    private String time_remain;
    private String buy_rent;
    private Date tran_date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTime_remain() {
        return time_remain;
    }

    public void setTime_remain(String time_remain) {
        this.time_remain = time_remain;
    }

    public String getBuy_rent() {
        return buy_rent;
    }

    public void setBuy_rent(String buy_rent) {
        this.buy_rent = buy_rent;
    }

    public Date getTran_date() {
        return tran_date;
    }

    public void setTran_date(Date tran_date) {
        this.tran_date = tran_date;
    }

    public Transaction(String title, int imageId, String time_remain, String buy_rent, Date tran_date) {

        this.title = title;
        this.imageId = imageId;
        this.time_remain = time_remain;
        this.buy_rent = buy_rent;
        this.tran_date = tran_date;
    }
}
