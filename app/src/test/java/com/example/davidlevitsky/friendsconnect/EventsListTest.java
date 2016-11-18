package com.example.davidlevitsky.friendsconnect;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by davidlevitsky on 11/17/16.
 */

public class EventsListTest {

    Event event1, event2;
    EventsList eventsList;

    @Before
    public void makeEvent() {
        event1 = new Event("fromTime", "toTime", "location", "date", "name");
        event2 = new Event("1", "2", "3", "4", "5");
        eventsList = EventsList.getInstance();
    }

    @Test
    public void testAddingEvents() {
        eventsList.addEvent(event1);
        eventsList.addEvent(event2);
        assertEquals(2, eventsList.getNumEvents());
    }

    @Test
    public void testDeletingEvents() {
        eventsList.deleteEvent(event1);
        eventsList.deleteEvent(event2);
        assertEquals(0, eventsList.getNumEvents());
    }


    public static void main(String [] args) {
        org.junit.runner.JUnitCore.main("ExampleUnitTest");
    }

}
