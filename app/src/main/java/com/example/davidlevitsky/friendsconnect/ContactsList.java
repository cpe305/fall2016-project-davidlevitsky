package com.example.davidlevitsky.friendsconnect;

import java.util.ArrayList;


/**
 * Created by davidlevitsky on 11/24/16.
 * This class is a Singleton pattern and is responsible for
 * maintaining the list of the user's contacts. There
 * is only one list of contacts, and reloading them continuously
 * would be expensive, so we will adopt this design pattern
 * for performance. All classes should be able to access this
 * information.
 */
public class ContactsList  {
    private ArrayList<Contact> contactsList;
    private int numContacts = 0;
    private static ContactsList instance = new ContactsList();

    private ContactsList() {
        this.contactsList = new ArrayList<Contact>();
    }

    public static ContactsList getInstance() {
        return instance;
    }

    public ArrayList<Contact> getContactsList() {
        return contactsList;
    }
    public void addContact(Contact contact) {
        contactsList.add(contact);
        numContacts++;
    }

    public void deleteContact(Event event) {
        if (contactsList.contains(event)) {
            contactsList.remove(event);
            numContacts--;
        }
    }

    public int getNumContacts() {
        return this.numContacts;
    }

}
