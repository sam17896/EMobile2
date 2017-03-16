package com.example.ahsan.emobile.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahsan.emobile.AppConfig;
import com.example.ahsan.emobile.HttpHandler;
import com.example.ahsan.emobile.ProfileView;
import com.example.ahsan.emobile.R;
import com.example.ahsan.emobile.SessionManager;
import com.example.ahsan.emobile.Fragments.MemberFragment;
import com.example.ahsan.emobile.Topic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class MemberAdapter extends ArrayAdapter implements View.OnClickListener{

    private Activity activity;
    private ArrayList<String> names;
    private static LayoutInflater inflater = null;
    boolean s;
    SessionManager session;
    Button b;
    public MemberAdapter (Activity activity, int resource, ArrayList<String> names, boolean s) {
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
            vi = inflater.inflate(R.layout.member, null);

        TextView name = (TextView) vi.findViewById(R.id.name);
        b = (Button) vi.findViewById(R.id.remove);

        String[] words = names.get(position).split(":");

        name.setText(words[0]);
        name.setTag(words[1]);
        name.setOnClickListener(this);

        if(!s || words[1].equals(session.getUserID())){
            b.setVisibility(View.GONE);
        }else{
            b.setTag(words[1]);
            b.setOnClickListener(this);
        }

        return vi;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.remove:
                b = (Button) v;
                myTask task = new myTask();
                task.execute(v.getTag().toString() + ":" + v.getId());

                break;
            case R.id.name:
                session.setProfile(v.getTag().toString());

                Intent i = new Intent(getContext(), ProfileView.class);
                getContext().startActivity(i);
                break;
        }
    }
    private class myTask extends AsyncTask<String, String , String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            HttpHandler sh = new HttpHandler();

            String words[] = params[0].split(":");

            String url = AppConfig.URL + "remove.php?id=" + session.getTopicID() + "&userid=" + words[0];
            sh.makeServiceCall(url);

            return words[1];
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            b.setText("Removed");
            b.setEnabled(false);
        }

    }

}

