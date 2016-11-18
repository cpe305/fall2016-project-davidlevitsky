package com.example.davidlevitsky.friendsconnect;

import android.app.usage.UsageEvents;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ContactTest {

    Contact contact;

    @Before
    public void makeContact() {
        contact = new Contact("David", "david@yahoo.com");
    }

    @Test
    public void contactConstructor() {
        assertEquals(contact.getName(), "David");

    }
    @Test
    public void testSetName() {
        contact.setName("Tester");
        assertEquals("Tester", contact.getName());
    }
    @Test
    public void testSetEMail() {
        contact.seteMail("e-mail");
        assertEquals("e-mail", contact.geteMail());
    }


    public static void main(String [] args) {
        org.junit.runner.JUnitCore.main("ExampleUnitTest");
    }
}