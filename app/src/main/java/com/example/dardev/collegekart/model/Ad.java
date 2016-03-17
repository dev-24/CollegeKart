package com.example.dardev.collegekart.model;

/**
 * Created by Dev on 03-03-2016.
 */
public class Ad {
    private String title;
    private String desc;
    private String price;
    private int imageId;
    private int period;
    private String seller;
    private String type;

    public String[] getAds() {
        return ads;
    }

    public void setAds(String[] ads) {
        this.ads = ads;
    }

    private String ads[];


    public int getPeriod() {

        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public Ad(String title, String desc, String price, int imageId) {

        this.title = title;
        this.desc = desc;
        this.price = price;
        this.imageId = imageId;
    }

    public Ad() {

    }









    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOption() {
        return title;

    }

    public void setOption(String title) {
        this.title = title;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
