package com.example.dardev.collegekart.model;

/**

 * Created by Dev on 04-03-2016.
 */
public class BuyRequest {
private String title;
    private int imageId;

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

    public BuyRequest(String title, int imageId) {

        this.title = title;
        this.imageId = imageId;
    }
}

