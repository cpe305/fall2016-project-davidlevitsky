package com.example.davidlevitsky.friendsconnect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class DisplayPastEventsActivity extends AppCompatActivity {

    private PastEventAdapter mAllEventsAdapter;
    private ArrayList<Event> pastEventsList = new ArrayList<Event>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_all_events);
        setup();
    }

    public void setup() {
        ListView lvEvents = (ListView)findViewById(R.id.lvDisplayAllEventsActivity);
        //access Singleton containing all events
        ArrayList<Event> eventsList = EventsList.getInstance().getEventsList();
        Collections.sort(eventsList);
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        String currentDate = getIntent().getStringExtra("date");
        pastEventsList.clear();
        try {
            Date currentDateTime = dateFormat.parse(currentDate);
            for (Event e : eventsList) {
                if (e.getDateTime().compareTo(currentDateTime) < 0) {
                    pastEventsList.add(e);
                }
            }
        }
        catch (ParseException e) {
            Log.e("FriendsConnect", "exception: " + e.getMessage());

        }


        mAllEventsAdapter = new PastEventAdapter(this, pastEventsList);
        lvEvents.setAdapter(mAllEventsAdapter);

    }
}
