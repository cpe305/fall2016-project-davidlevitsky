package com.example.davidlevitsky.friendsconnect;

import android.content.Intent;
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

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

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

        setup();
        realm.close();

    }

    public void setup() {
        Button btnSave = (Button) findViewById(R.id.bSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            //After user saves an event, confirm it and take them back to main page
            public void onClick(View v) {
                createNewEvent();
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
        String currentDate = getIntent().getStringExtra("date");
        etDateString.setText(currentDate);

    }

    public void createNewEvent() {

        final String name = etEventName.getText().toString();
        final String date = etDateString.getText().toString();

        Toast toast = Toast.makeText(getApplicationContext(), "creation works: " + name, Toast.LENGTH_LONG);
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Event event = realm.createObject(Event.class);
                event.setName(name);
                event.setDate(date);
                event.setLocation("test location");
                event.setFromTime(fromTime.toString());
                event.setToTime(toTime.toString());

            }
        });

        toast.show();
    }


}
