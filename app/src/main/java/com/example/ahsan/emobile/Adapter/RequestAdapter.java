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
import com.example.ahsan.emobile.Topic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class RequestAdapter extends ArrayAdapter implements View.OnClickListener{

    private Activity activity;
    private ArrayList<String> names;
    private static LayoutInflater inflater = null;
    Button b , r;
    SessionManager session;
    boolean s;
    public RequestAdapter (Activity activity, int resource, ArrayList<String> names, boolean s) {
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
            vi = inflater.inflate(R.layout.request, null);

        TextView name = (TextView) vi.findViewById(R.id.name);
        b = (Button) vi.findViewById(R.id.add);
        r = (Button) vi.findViewById(R.id.reject);

        String[] words = names.get(position).split(":");

        name.setText(words[0]);
        name.setTag(words[1]);
        name.setOnClickListener(this);

        if(!s){
            b.setVisibility(View.GONE);
            r.setVisibility(View.GONE);
        }else {
            b.setTag(words[1]);
            b.setOnClickListener(this);
            r.setTag(words[1]);
            r.setOnClickListener(this);
        }

        return vi;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.add:
                b = (Button) v;
                myTask task = new myTask();
                task.execute(v.getTag().toString());
                break;

            case R.id.reject:
                b = (Button) v;

                myTask1 task1 = new myTask1();
                task1.execute(v.getTag().toString());
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
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            b.setText(b.getText()+"ed");
            b.setEnabled(false);
        }

        @Override
        protected String doInBackground(String... params) {

            HttpHandler sh = new HttpHandler();
            String url = AppConfig.URL + "add_user.php?id=" + session.getTopicID() + "&userid=" + params[0];
            sh.makeServiceCall(url);

            return null;
        }

    }
    private class myTask1 extends AsyncTask<String, String , String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            r.setText("Remove");
            r.setEnabled(false);
        }

        @Override
        protected String doInBackground(String... params) {

            HttpHandler sh = new HttpHandler();
            String url = AppConfig.URL + "remove.php?id=" + session.getTopicID() + "&userid=" + params[0];
            sh.makeServiceCall(url);

            return null;
        }

    }


}

