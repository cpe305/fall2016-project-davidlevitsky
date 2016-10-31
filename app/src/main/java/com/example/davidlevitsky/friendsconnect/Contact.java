package com.example.davidlevitsky.friendsconnect;

/**
 * Created by davidlevitsky on 10/30/16.
 */
public class Contact {

    private String name;
    private String eMail;

    public Contact(String name, String eMail) {
        this.name = name;
        this.eMail = eMail;
    }

    public String getName() {
        return name;
    }

    public String geteMail() {
        return eMail;
    }

}
