package com.example.davidlevitsky.friendsconnect;

import android.app.usage.UsageEvents;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class EventTester {

    Event event;

    @Before
    public void makeEvent() {
        event = new Event("fromTime", "toTime", "location", "date", "name");
    }

    @Test
    public void testSetName() {
        event.setName("Tester");
        assertEquals("Tester", event.getName());
    }

    @Test
    public void testSetDate() {
        event.setDate("Test Date");
        assertEquals("Test Date", event.getDate());
    }

    @Test
    public void testSetToTime() {
        event.setToTime("9:00");
        assertEquals("9:00", event.getToTime());
    }

    @Test
    public void testSetFromTime() {
        event.setFromTime("7:00");
        assertEquals("7:00", event.getFromTime());
    }

    @Test
    public void testSetLocation() {
        event.setLocation("location1");
        assertEquals("location1", event.getLocation());
    }


    public static void main(String [] args) {
        org.junit.runner.JUnitCore.main("ExampleUnitTest");
    }
}