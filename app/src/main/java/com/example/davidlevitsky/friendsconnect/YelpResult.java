package com.example.davidlevitsky.friendsconnect;

/**
 * Created by davidlevitsky on 11/23/16.
 */

public class YelpResult {
    private String name;
    private String address;
    private String rating;

    private String pictureURL;

    public YelpResult(String name, String rating , String address, String pictureURL) {
        this.name = name;
        this.rating = rating;
        this.address = address;
        this.pictureURL = pictureURL;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }



}
