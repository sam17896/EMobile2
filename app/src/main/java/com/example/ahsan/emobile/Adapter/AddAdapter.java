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
import android.widget.ImageButton;
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


public class AddAdapter extends ArrayAdapter implements View.OnClickListener{

    private Activity activity;
    private ArrayList<String> names;
    int currPos;
    SessionManager session;
    ImageButton b;
    private static LayoutInflater inflater = null;
    boolean s;
    public AddAdapter (Activity activity, int resource, ArrayList<String> names, boolean s) {
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
            vi = inflater.inflate(R.layout.add, null);

        TextView name = (TextView) vi.findViewById(R.id.name);
        b = (ImageButton) vi.findViewById(R.id.add);

        String[] words = names.get(position).split(":");

        name.setText(words[0]);
        name.setTag(words[1]);
        name.setOnClickListener(this);

        if(!s){
            b.setVisibility(View.GONE);
        }else{
            b.setOnClickListener(this);
            b.setTag(words[1]+":"+position);
        }


        return vi;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.add:
                b = (ImageButton) v;
                myTask task = new myTask();
                String[] tags = v.getTag().toString().split(":");
                currPos = Integer.getInteger(tags[1]);
                task.execute(tags[0]);
                break;

            case R.id.name:
                session.setProfile(v.getTag().toString());
                Toast.makeText(activity, v.getTag().toString() , Toast.LENGTH_SHORT).show();

                Intent i = new Intent(getContext(), ProfileView.class);
                getContext().startActivity(i);
                break;
        }
    }
    private class myTask extends AsyncTask<String, String , String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            b.setImageResource(R.drawable.icon_added);
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
}

