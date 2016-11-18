package com.example.davidlevitsky.friendsconnect;

import android.content.Intent;
import android.media.Image;
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
    private EventAdapter mEventAdapter;

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
       // Toast.makeText(getBaseContext(), "\nNumber of events: " + realm.where(Event.class).count(), Toast.LENGTH_SHORT).show();

    }

    public void setup() {
        RealmResults<Event> results = realm.where(Event.class).findAll();

//

        EventsList eventsList = EventsList.getInstance();
        if (eventsList.getNumEvents() == 0) {

            for (Event e : results) {
                eventsList.addEvent(e);
            }
        }
        mEventAdapter = new EventAdapter(this, eventsList.getEventsList());
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
        //refresh the adapter at the end of setting up.
        //list in data could have changed because we could have come from CreateEventActivity
        mEventAdapter.notifyDataSetChanged();



    }

    
    public void onClickDelete(View view) {
        int position = (Integer)view.getTag();
        Event event = EventsList.getInstance().getEventsList().get(position);
        final String eventName = event.getName();
        mEventAdapter.remove(event);
        mEventAdapter.notifyDataSetChanged();
        final RealmObject objectToDelete = realm.where(Event.class).equalTo("name", event.getName()).findFirst();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                objectToDelete.deleteFromRealm();
                Toast toast = Toast.makeText(getApplicationContext(), "Event " + eventName + " removed.", Toast.LENGTH_SHORT);
                toast.show();

            }
        });

    }


}
