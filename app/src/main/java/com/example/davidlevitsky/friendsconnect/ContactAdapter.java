package com.example.davidlevitsky.friendsconnect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by davidlevitsky on 11/24/16.
 */

public class ContactAdapter extends ArrayAdapter<Contact> {
    public ContactAdapter(Context context, ArrayList<Contact> contacts) {
        super(context, 0, contacts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Contact contact = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.contact_adapter, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.tvContactName);
        TextView tvEmail = (TextView) convertView.findViewById(R.id.tvContactEmail);
        // Populate the data into the template view using the data object
        tvName.setText(contact.getName());
        tvEmail.setText(contact.geteMail());
        // Return the completed view to render on screen
        return convertView;
    }

}
