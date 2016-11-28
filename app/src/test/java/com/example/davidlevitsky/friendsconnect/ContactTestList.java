package com.example.davidlevitsky.friendsconnect;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by davidlevitsky on 11/27/16.
 */

public class ContactTestList {

    Contact contact;
    Contact secondContact;
    ContactsList testList;

    @Before
    public void setup() {
        contact = new Contact("David", "david@yahoo.com");
        secondContact = new Contact("Not david", "fake@gmail.com");
        testList = ContactsList.getInstance();
    }


    @Test
    public void testDeleteEvent() {
        testList.deleteAllContacts();
        assertEquals(0, testList.getNumContacts());
        testList.addContact(contact);
        testList.addContact(secondContact);
        testList.deleteContact(secondContact);
        assertEquals(testList.getNumContacts(), 1);

    }

    @Test
    public void testAddContact() {
        testList.deleteAllContacts();
        assertEquals(0, testList.getNumContacts());
        testList.addContact(contact);
        testList.addContact(secondContact);
        assertEquals(testList.getNumContacts(), 2);
    }




}
