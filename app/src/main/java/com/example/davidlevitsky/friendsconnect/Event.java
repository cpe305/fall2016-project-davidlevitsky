package com.example.davidlevitsky.friendsconnect;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

import io.realm.RealmObject;

/**
 * Created by davidlevitsky on 10/16/16.
 * This class is the representation of an Event object. It is open for extension
 * but closed for modification. Any additional event manipulation will be done
 * with an EventModifier class, which will be created if deemed necessary.
 */

public class Event extends RealmObject implements Comparable<Event>{
	private String fromTime;
    private String toTime;
	private String location;
	private String date;
    private String name;
    private String imageUrl;
    private String rating;
    private String address;
    private Date dateTime;
    private String userRating;

    public Event() {}

	public Event(String name, String fromTime, String toTime, String location, String date, String imageUrl) {
        this.name = name;
		this.fromTime = fromTime;
        this.toTime = toTime;
		this.location = location;
		this.date = date;
        this.imageUrl = imageUrl;
        //convert date into Date object so Events can be compared to each other

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

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(String date) {
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        try {
            Date dateTime = format.parse(date);
            this.dateTime = dateTime;
        }
        catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }

    @Override
    public int compareTo(Event event) {
        return this.dateTime.compareTo(event.getDateTime());
    }


}

