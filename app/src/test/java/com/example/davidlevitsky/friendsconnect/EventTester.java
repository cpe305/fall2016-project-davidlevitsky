package com.example.davidlevitsky.friendsconnect;

import android.app.usage.UsageEvents;

import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class EventTester {

    Event event;

    @Before
    public void makeEvent() {
        event = new Event("fromTime", "toTime", "location", "date", "name", "");
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

    @Test
    public void testSetImageURL() {
        event.setImageUrl("imageURL");
        assertEquals("imageURL", event.getImageUrl());
    }

    @Test
    public void testSetAddress() {
        event.setAddress("address");
        assertEquals("address", event.getAddress());
    }

    @Test
    public void testSetDateTime() {
        event.setDateTime("10/25/1996");
        String date = "10/25/1996";
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        try {
            Date dateTime = format.parse(date);
            assertEquals(dateTime, event.getDateTime());
        }
        catch (ParseException e) {
            System.out.println(e.getMessage());
        }

    }


    public static void main(String [] args) {
        org.junit.runner.JUnitCore.main("ExampleUnitTest");
    }
}