package com.example.davidlevitsky.friendsconnect;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setup();

    }

    public void setup() {
        ArrayList<Event> eventsList = EventsList.getInstance().getEventsList();
       // eventsList.add(new Event("name", "time", "location", "date"));
        EventAdapter mEventAdapter = new EventAdapter(this, eventsList);
        ListView listView = (ListView) findViewById(R.id.lvEvents);
        listView.setAdapter(mEventAdapter);

        CalendarView calendarView = (CalendarView)findViewById(R.id.mCalendarView);

        ImageButton btnCreateEvent = (ImageButton)findViewById(R.id.imageButton2);
        btnCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateEventActivity.class);
                //pass data from selected data to onCreateActivity
                intent.putExtra("date", currentDate);
                startActivity(intent);
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {

                currentDate = String.valueOf(month + "/" + dayOfMonth + "/" + year);
            }
        });
    }









}
