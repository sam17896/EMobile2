package com.example.ahsan.emobile.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahsan.emobile.R;
import com.example.ahsan.emobile.Topic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class ContactAdapter extends ArrayAdapter{

    private Activity activity;
    private ArrayList<String> names;
    private static LayoutInflater inflater = null;
    boolean s;
    public ContactAdapter (Activity activity, int resource, ArrayList<String> names, boolean s) {
        super(activity, resource,  names);

        this.activity = activity;
        this.names = names;
        this.s = s;

        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return names.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if(convertView == null)
            vi = inflater.inflate(R.layout.contact, null);

        TextView type = (TextView) vi.findViewById(R.id.contact_type);
        TextView number = (TextView) vi.findViewById(R.id.contact_number);

        String[] words = names.get(position).split(":");

        type.setText(words[0]);
        number.setText(words[1]);

        return vi;
    }
}

