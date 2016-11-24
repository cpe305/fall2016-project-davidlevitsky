package com.example.davidlevitsky.friendsconnect;

import io.realm.RealmObject;

/**
 * Created by davidlevitsky on 10/16/16.
 * This class is the representation of an Event object. It is open for extension
 * but closed for modification. Any additional event manipulation will be done
 * with an EventModifier class, which will be created if deemed necessary.
 */

public class Event extends RealmObject {
	private String fromTime;
    private String toTime;
	private String location;
	private String date;
    private String name;
    private String imageUrl;
    private String rating;
    private String address;

    public Event() {}

	public Event(String name, String fromTime, String toTime, String location, String date, String imageUrl) {
        this.name = name;
		this.fromTime = fromTime;
        this.toTime = toTime;
		this.location = location;
		this.date = date;
        this.imageUrl = imageUrl;
	}

    public void setName(String name) {
        this.name = name;
    }

    public void setFromTime(String time) {
        this.fromTime = time;
    }

    public void setToTime(String time) {
        this.toTime = time;
    }


    public void setDate(String date) {
        this.date = date;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return this.name;
    }

    public String getFromTime() {
        return this.fromTime;
    }

    public String getToTime() {
        return this.toTime;
    }


    public String getLocation() {
        return this.location;
    }

    public String getDate() {
        return this.date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


}

