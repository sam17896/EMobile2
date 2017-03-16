package com.example.ahsan.emobile.Fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahsan.emobile.Adapter.MemberAdapter;
import com.example.ahsan.emobile.AppConfig;
import com.example.ahsan.emobile.HttpHandler;
import com.example.ahsan.emobile.R;
import com.example.ahsan.emobile.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class PersonalInformationFragment extends Fragment {

    TextView firstname, lastname, gender, country, dob;
    String fn,ln,g,c,db;
    ArrayList<String> map;
    boolean s;
    SessionManager session;
    String jsonStr;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_pi, container, false);

        session = new SessionManager(getContext().getApplicationContext());

        firstname = (TextView) rootView.findViewById(R.id.user_ofn);
        lastname = (TextView) rootView.findViewById(R.id.user_oln);
        gender = (TextView) rootView.findViewById(R.id.user_og);
        country = (TextView) rootView.findViewById(R.id.user_oc);
        dob = (TextView) rootView.findViewById(R.id.user_odob);

        MyTask task = new MyTask();
        task.execute();

        return rootView;
    }

    private class MyTask extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... s) {

            HttpHandler sh = new HttpHandler();

            String url = AppConfig.URL +"detail.php?id=" + session.getProfile();
            LoadDetail(url, sh);
            return null;
        }

        @Override
        protected void onPostExecute(String str) {
            super.onPostExecute(str);

            firstname.setText(fn);
            lastname.setText(ln);
            gender.setText(g);
            country.setText(c);
            dob.setText(db);


        }

        @Override
        protected void onProgressUpdate(String... values) {
        }

        public void LoadDetail(String url, HttpHandler sh){
            jsonStr = sh.makeServiceCall(url);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);


                    JSONArray member = jsonObj.getJSONArray("detail");
                    JSONObject d  = member.getJSONObject(0);
                    fn = d.getString("fn");
                    ln = d.getString("ln");
                    g = d.getString("g");
                    c = d.getString("c");
                    db = d.getString("dob");



                } catch (final JSONException e) {

                }
            } else {

            }
        }
    }
}