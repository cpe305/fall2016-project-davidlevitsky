package com.example.davidlevitsky.friendsconnect;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
import java.util.ArrayList;
import java.util.HashMap;
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
                if (!createNewEvent()) {
                    return;
                }
                Intent intent = new Intent(CreateEventActivity.this, MainActivity.class);
                Toast toast = Toast.makeText(getApplicationContext(), "Event Saved!", Toast.LENGTH_SHORT);
                toast.show();
                startActivity(intent);
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
        String currentDate = getIntent().getStringExtra("date");
        etDateString.setText(currentDate);

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

    public boolean createNewEvent() {

        final String name = etEventName.getText().toString();
        final String date = etDateString.getText().toString();
        final String location = etLocation.getText().toString();
        final String startTime = fromTime.getText().toString();
        final String endTime = toTime.getText().toString();
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
                if (imageURL != null) {
                    event.setImageUrl(imageURL);
                }
                EventsList.getInstance().addEvent(event);


            }
        });
        return success;


    }


}
