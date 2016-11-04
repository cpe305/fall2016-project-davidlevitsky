package com.example.davidlevitsky.friendsconnect;

import java.util.ArrayList;

/**
 * Created by davidlevitsky on 10/30/16.
 * This class is a Singleton pattern and is responsible for
 * maintaining the list of the user's scheduled events. There
 * should only be one schedule, so it is important that this class
 * is a Singleton.
 */
public class EventsList {
    private ArrayList<Event> eventsList;
    private static EventsList instance = new EventsList();

    private EventsList() {
        this.eventsList = new ArrayList<Event>();
    }

    public static EventsList getInstance() {
        return instance;
    }

    public ArrayList<Event> getEventsList() {
        return eventsList;
    }
    public void addEvent(Event event) {
        eventsList.add(event);
    }

    public void deleteEvent(Event event) {
        eventsList.remove(event);
    }

}
