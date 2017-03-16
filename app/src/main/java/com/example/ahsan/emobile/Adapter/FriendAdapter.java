package com.example.ahsan.emobile.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahsan.emobile.ProfileView;
import com.example.ahsan.emobile.R;
import com.example.ahsan.emobile.SessionManager;
import com.example.ahsan.emobile.Topic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class FriendAdapter extends ArrayAdapter implements View.OnClickListener{

    private Activity activity;
    private ArrayList<String> names;
    private static LayoutInflater inflater = null;
    boolean s;
    SessionManager session;
    public FriendAdapter (Activity activity, int resource, ArrayList<String> names, boolean s) {
        super(activity, resource,  names);

        this.activity = activity;
        this.names = names;
        this.s = s;
        session = new SessionManager(getContext().getApplicationContext());
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
            vi = inflater.inflate(R.layout.friend, null);

        TextView name = (TextView) vi.findViewById(R.id.friend);
        String words[] = names.get(position).split(":");

        // TODO SET IMAGE VIEW

        name.setText(words[0]);
        name.setTag(words[1]);
        name.setOnClickListener(this);
        return vi;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.friend:

                session.setProfile(v.getTag().toString());
                Toast.makeText(activity, v.getTag().toString() , Toast.LENGTH_SHORT).show();
                
                Intent i = new Intent(getContext(), ProfileView.class);
                getContext().startActivity(i);
                break;
        }
    }
}

