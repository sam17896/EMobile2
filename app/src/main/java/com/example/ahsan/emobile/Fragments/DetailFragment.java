package com.example.ahsan.emobile.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ahsan.emobile.R;
import com.example.ahsan.emobile.SessionManager;

public class DetailFragment extends Fragment {

    SessionManager session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vi = inflater.inflate(R.layout.fragment_detail, container, false);

        session = new SessionManager(getContext().getApplicationContext());

        TextView title = (TextView) vi.findViewById(R.id.topicName);
        TextView description = (TextView) vi.findViewById(R.id.description);
        TextView admin = (TextView) vi.findViewById(R.id.adminName);

        title.setText(session.getTopicTitle());
        description.setText(session.getTopicDescription());
        admin.setText(session.getTopicAdmin());

        return vi;
    }

}