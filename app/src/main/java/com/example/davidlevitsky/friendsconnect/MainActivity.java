package com.example.davidlevitsky.friendsconnect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setup();


    }

    public void setup() {
        ArrayList<Event> eventsList = new ArrayList<Event>();
        eventsList.add(new Event("name", "time", "location", "date"));
        EventAdapter mEventAdapter = new EventAdapter(this, eventsList);
        ListView listView = (ListView) findViewById(R.id.lvEvents);
        listView.setAdapter(mEventAdapter);



    }
}
