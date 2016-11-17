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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private String currentDate;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RealmSetup();
        setup();

    }

    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }


    public void RealmSetup() {
        Realm.init(getBaseContext());
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfiguration);

        realm = Realm.getDefaultInstance();
        Toast.makeText(getBaseContext(), "\nNumber of events: " + realm.where(Event.class).count(), Toast.LENGTH_SHORT).show();

    }

    public void setup() {
        RealmResults<Event> results = realm.where(Event.class).findAll();
        ArrayList<Event> eventsList = new ArrayList<Event>();
        for (Event e : results) {
            eventsList.add(e);
        }

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
