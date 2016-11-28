package com.example.davidlevitsky.friendsconnect;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by davidlevitsky on 11/27/16.
 */

public class YelpResultTest {
    YelpResult yelpResult;

    @Before
    public void makeYelpResult() { this.yelpResult = new YelpResult("name", "rating" , "address", "pictureURL", "numReviews",
                                "description", "mobileURL");
    }

    @Test
    public void testSetAddress() {
        yelpResult.setAddress("new address");
        assertEquals("new address", yelpResult.getAddress());
    }

    @Test
    public void testSetRating() {
        yelpResult.setRating("5");
        assertEquals("5", yelpResult.getRating());
    }

    @Test
    public void testSetName() {
        yelpResult.setName("new name");
        assertEquals("new name", yelpResult.getName());
    }

    @Test
    public void testSetPictureURL() {
        yelpResult.setPictureURL("new URL");
        assertEquals("new URL", yelpResult.getPictureURL());
    }

    @Test
    public void testSetNumReviews() {
        yelpResult.setNumReviews("10");
        assertEquals("10", yelpResult.getNumReviews());
    }

    @Test
    public void testSetDescription() {
        yelpResult.setDescription("new description");
        assertEquals("new description", yelpResult.getDescription());
    }

    @Test
    public void testSetMobileURL() {
        yelpResult.setMobileURL("new mobile");
        assertEquals("new mobile", yelpResult.getMobileURL());
    }

}
