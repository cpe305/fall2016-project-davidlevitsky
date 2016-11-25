package com.example.davidlevitsky.friendsconnect;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yelp.clientlib.connection.YelpAPI;
import com.yelp.clientlib.connection.YelpAPIFactory;
import com.yelp.clientlib.entities.Business;
import com.yelp.clientlib.entities.SearchResponse;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by davidlevitsky on 10/24/16.
 * This class is responsible for a full Activity and is used to
 * create new events. It will get an instance of the Singleton object
 * EventsList and append new events to it.
 */
public class CreateEventActivity extends AppCompatActivity {
    private CheckBox timeCheckbox;
    private EditText fromTime;
    private EditText toTime;
    private EditText etEventName;
    private EditText etDateString;
    private EditText etLocation;
    private Button bSearchEvent;
    private EditText etAddress;
    private String imageURL;
    private String rating;
    private ContactAdapter mContactAdapter;
    ListView lvContacts;
    Contact selectedContact;
    private TextView tvInvitedContact;
    private final int REQUEST_CODE = 200; //arbitrary request code to receive data from a launched activity


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        Realm.init(getBaseContext());
        Realm realm = Realm.getDefaultInstance();

        timeCheckbox = (CheckBox)findViewById(R.id.checkBox);
        fromTime = (EditText)findViewById(R.id.etFromTime);
        toTime = (EditText)findViewById(R.id.etToTime);
        etDateString = (EditText)findViewById(R.id.etDate);
        etEventName = (EditText)findViewById(R.id.etEventName);
        bSearchEvent = (Button)findViewById(R.id.bSearchEvent);
        etLocation = (EditText)findViewById(R.id.etLocation);
        etAddress = (EditText)findViewById(R.id.etAddress);

        setup();
        realm.close();

    }


    public void setup() {
        Button btnSave = (Button) findViewById(R.id.bSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            //After user saves an event, confirm it and take them back to main page
            public void onClick(View v) {
                Intent data = new Intent();
                //sendEmailNotification();
                if (!createNewEvent()) {
                    return;
                }
                else {
                    sendEmailNotification();
                    // Pass relevant data back as a result
                    data.putExtra("code", 1); // ints work too
                    // Activity finished ok, return the data
                    setResult(RESULT_OK, data); // set result code and bundle data for response
//                Intent intent = new Intent(CreateEventActivity.this, MainActivity.class);
//                //startActivity(intent);
                    finish();
                }
            }

        });
        timeCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (isChecked) {
                    fromTime.setText("12:00");
                    toTime.setText("12:00");
                }
                else {
                    fromTime.setText("");
                    toTime.setText("");
                }
            }
        }
        );
        bSearchEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateEventActivity.this, SearchEventYelpActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }

        });
        tvInvitedContact = (TextView)findViewById(R.id.tvInvitedContact);
        String currentDate = getIntent().getStringExtra("date");
        etDateString.setText(currentDate);
        mContactAdapter = new ContactAdapter(this, ContactsList.getInstance().getContactsList());
        lvContacts = (ListView) findViewById(R.id.listView);
        lvContacts.setAdapter(mContactAdapter);
        lvContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact contact = ContactsList.getInstance().getContactsList().get(position);
                tvInvitedContact.setText(contact.getName());
                selectedContact = contact;
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
           // String name = data.getExtras().getString("name");
            //int code = data.getExtras().getInt("position", 0);
            // Toast the name to display temporarily on screen
            etLocation.setText(data.getStringExtra("name"));
            etAddress.setText(data.getStringExtra("location"));
            imageURL = data.getStringExtra("url");
            rating = data.getStringExtra("rating");
        }
    }

    public void sendEmailNotification() {
        String name = etEventName.getText().toString();
        String date = etDateString.getText().toString();
        String location = etLocation.getText().toString();
        String startTime = fromTime.getText().toString();
        String endTime = toTime.getText().toString();
        String contactName = selectedContact.getName();
        String contactEMail = selectedContact.geteMail();

        String bodyOfEmail = "Hey " + contactName + "!\nI'd like to invite you to my event: " + name + ". It will run from ";
        bodyOfEmail += startTime + " until " + endTime + " at the following location: " + location + " on " +
                date + ". Hope to see you there!";

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{contactEMail});
        i.putExtra(Intent.EXTRA_SUBJECT, "Invitation to " + name);
        i.putExtra(Intent.EXTRA_TEXT   , bodyOfEmail);

        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(CreateEventActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean createNewEvent() {

        final String name = etEventName.getText().toString();
        final String date = etDateString.getText().toString();
        final String location = etLocation.getText().toString();
        final String startTime = fromTime.getText().toString();
        final String endTime = toTime.getText().toString();
        final String contactName = tvInvitedContact.getText().toString();
        boolean success = true;

        // Light error checking
        // Must be done on case-by-case basis to provide accurrate
        // error message to the user.
        Toast toast;
        Context context = getApplicationContext();

        if (name.isEmpty()) {
            toast = Toast.makeText(context, "Please enter an event name", Toast.LENGTH_SHORT);
            toast.show();
            success = false;
        }

        else if (date.isEmpty()) {
            toast = Toast.makeText(context, "Please enter a date", Toast.LENGTH_SHORT);
            toast.show();
            success = false;
        }

        else if (location.isEmpty()) {
            toast = Toast.makeText(context, "Please enter a location", Toast.LENGTH_SHORT);
            toast.show();
            success = false;
        }

        else if (startTime.isEmpty()) {
            toast = Toast.makeText(context, "Please enter a start time", Toast.LENGTH_SHORT);
            toast.show();
            success = false;
        }

        else if (endTime.isEmpty()) {
            toast = Toast.makeText(context, "Please enter an end time", Toast.LENGTH_SHORT);
            toast.show();
            success = false;

        }

        else if (contactName.isEmpty()) {
            toast = Toast.makeText(context, "Please select a contact", Toast.LENGTH_SHORT);
            toast.show();
            success = false;
        }
        else {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Event event = realm.createObject(Event.class);
                    event.setName(name);
                    event.setDate(date);
                    event.setLocation(etLocation.getText().toString());
                    event.setFromTime(fromTime.getText().toString());
                    event.setToTime(toTime.getText().toString());
                    event.setRating(rating);
                    event.setAddress(etAddress.getText().toString());
                    event.setDateTime(date);
                    if (imageURL != null) {
                        event.setImageUrl(imageURL);
                    }
                    EventsList.getInstance().addEvent(event);
                    EventsList.getInstance().sortEvents();


                }
            });
        }

        return success;


    }


}
