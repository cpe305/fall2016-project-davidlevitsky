package com.example.davidlevitsky.friendsconnect;

import android.app.usage.UsageEvents;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {

    Contact contact;

    @Before
    public void makeContact() {
        contact = new Contact("name", "e-mail");
    }

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void contactConstructor() {
        assertEquals(contact.getName(), "name");

    }


    public static void main(String [] args) {
        org.junit.runner.JUnitCore.main("ExampleUnitTest");
    }
}