package com.example.davidlevitsky.friendsconnect;

/**
 * Created by davidlevitsky on 10/16/16.
 * This class is the representation of an Event object. It is open for extension
 * but closed for modification. Any additional event manipulation will be done
 * with an EventModifier class, which will be created if deemed necessary.
 */

public class Event {
	private String time;
	private String location;
	private String date;
    private String name;
	
	public Event(String name, String time, String location, String date)
	{
        this.name = name;
		this.time = time;
		this.location = location;
		this.date = date;
	}

    public String getName() {
        return this.name;
    }

    public String getTime() {
        return this.time;
    }

    public String getLocation() {
        return this.location;
    }

    public String getDate() {
        return this.date;
    }

}
