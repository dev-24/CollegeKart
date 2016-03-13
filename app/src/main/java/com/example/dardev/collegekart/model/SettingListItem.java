package com.example.dardev.collegekart.model;

/**
 * Created by Dev on 15-01-2016.
 */
public class SettingListItem {
    private String option;
    private int imageId;

    public SettingListItem(String option, int imageId) {
        this.option = option;
        this.imageId = imageId;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
