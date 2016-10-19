package com.example.davidlevitsky.friendsconnect;

import java.util.ArrayList;
import java.util.Date;

public class Data {
	private ArrayList<Event> events;
	private int eventSize;
	private Date currentDay;
	
	public Data(ArrayList<Event> events, int eventSize, Date currentDay) {
		this.events = events;
		this.eventSize = eventSize;
		this.currentDay = currentDay;
	}
	
	public void addEvent(Event event) {
		events.add(event);
	}
	
	public void removeEvent(Event event) {
		events.remove(event);
	}
}
