package com.example.davidlevitsky.friendsconnect;

/**
 * Created by davidlevitsky on 11/23/16.
 */

public class YelpResult {
    private String name;
    private String address;
    private String rating;
    private String numReviews;
    private String description;
    private String mobileURL;

    private String pictureURL;

    public YelpResult(String name, String rating , String address, String pictureURL, String numReviews,
                      String description, String mobileURL) {
        this.name = name;
        this.rating = rating;
        this.address = address;
        this.pictureURL = pictureURL;
        this.numReviews = numReviews;
        this.description = description;
        this.mobileURL = mobileURL;
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

    public String getNumReviews() {
        return numReviews;
    }

    public void setNumReviews(String numReviews) {
        this.numReviews = numReviews;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMobileURL() {
        return mobileURL;
    }

    public void setMobileURL(String mobileURL) {
        this.mobileURL = mobileURL;
    }



}
