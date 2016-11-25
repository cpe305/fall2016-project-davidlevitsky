package com.example.davidlevitsky.friendsconnect;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.Image;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;

import static android.Manifest.permission.READ_CONTACTS;

public class MainActivity extends AppCompatActivity {

    private String currentDate;
    private Realm realm;
    private EventAdapter mEventAdapter;
    private static final int REQUEST_READ_CONTACTS = 0;
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RealmSetup();
        setup();
        //get list of contacts from phone by populating ContactsList singleton
        ArrayList<String> contactList = getNameEmailDetails();
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //do nothing
            }


        });


        CalendarView calendarView = (CalendarView)findViewById(R.id.mCalendarView);

        ImageButton btnCreateEvent = (ImageButton)findViewById(R.id.imageButton2);
        btnCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateEventActivity.class);
                //pass data from selected data to onCreateActivity
                intent.putExtra("date", currentDate);
                startActivityForResult(intent, REQUEST_CODE);
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


    public void onClickViewDetails(View view) {
        int position = (Integer)view.getTag();
        Event event = EventsList.getInstance().getEventsList().get(position);
        Intent i = new Intent(MainActivity.this, DisplayEventInfoActivity.class);
        // put "extras" into the bundle for access in the second activity
        i.putExtra("name", event.getName());
        i.putExtra("location", event.getLocation());
        i.putExtra("address", event.getAddress());
        i.putExtra("rating", event.getRating());
        i.putExtra("fromTime", event.getFromTime());
        i.putExtra("toTime", event.getToTime());
        i.putExtra("url", event.getImageUrl());
        i.putExtra("participant", "John Doe");
        // brings up the second activity
        startActivity(i);
    }

    public void onClickDelete(View view) {
        if (view == null || view.getTag() == null) {
            return;
        }
        int position = (Integer)view.getTag();
        Toast toast = Toast.makeText(getApplicationContext(), Integer.toString(position), Toast.LENGTH_SHORT);
        toast.show();
        Event event = EventsList.getInstance().getEventsList().get(position);
        final String eventName = event.getName();
        mEventAdapter.remove(event);
        mEventAdapter.notifyDataSetChanged();
        EventsList.getInstance().deleteEvent(event);
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



    public ArrayList<String> getNameEmailDetails() {
        ArrayList<String> emlRecs = new ArrayList<String>();
        HashSet<String> emlRecsHS = new HashSet<String>();
        Context context = getApplicationContext();
        ContentResolver cr = context.getContentResolver();
        String[] PROJECTION = new String[] { ContactsContract.RawContacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.PHOTO_ID,
                ContactsContract.CommonDataKinds.Email.DATA,
                ContactsContract.CommonDataKinds.Photo.CONTACT_ID };
        String order = "CASE WHEN "
                + ContactsContract.Contacts.DISPLAY_NAME
                + " NOT LIKE '%@%' THEN 1 ELSE 2 END, "
                + ContactsContract.Contacts.DISPLAY_NAME
                + ", "
                + ContactsContract.CommonDataKinds.Email.DATA
                + " COLLATE NOCASE";
        String filter = ContactsContract.CommonDataKinds.Email.DATA + " NOT LIKE ''";
        Cursor cur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, PROJECTION, filter, null, order);
        if (cur.moveToFirst()) {
            do {
                // names comes in hand sometimes
                String name = cur.getString(1);
                String emlAddr = cur.getString(3);

                // keep unique only
                if (emlRecsHS.add(emlAddr.toLowerCase())) {
                    emlRecs.add(emlAddr);
                    Contact contact = new Contact(name, emlAddr);
                    ContactsList.getInstance().addContact(contact);

                }
            } while (cur.moveToNext());
        }

        cur.close();
        Toast toast = Toast.makeText(getApplicationContext(), "success " + Integer.toString(emlRecs.size()), Toast.LENGTH_LONG);
        toast.show();
        return emlRecs;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            // Toast the name to display temporarily on screen
            Toast.makeText(this, "Event Saved!", Toast.LENGTH_SHORT).show();
        }
        mEventAdapter.notifyDataSetChanged();
    }

}
