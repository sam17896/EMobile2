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
import android.widget.Toast;

import com.example.ahsan.emobile.Adapter.EducationAdapter;
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

public class EducationFragment extends Fragment {

    ListView lv;
    ArrayList<String> map;
    boolean s;
    SessionManager session;
    String jsonStr;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_education, container, false);


        session = new SessionManager(getContext().getApplicationContext());
        lv = (ListView) rootView.findViewById(R.id.education);

        map = new ArrayList<>();

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

            String url = AppConfig.URL +"loadeducation.php?id=" + session.getProfile();
            LoadEducation(url, sh);
            return null;
        }

        @Override
        protected void onPostExecute(String str) {
            super.onPostExecute(str);

            ListAdapter adapter = new EducationAdapter(getActivity(),R.layout.education, map ,s);
            lv.setAdapter(adapter);


        }

        @Override
        protected void onProgressUpdate(String... values) {
        }

        public void LoadEducation(String url, HttpHandler sh){
            jsonStr = sh.makeServiceCall(url);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    JSONArray member = jsonObj.getJSONArray("education");

                    for (int i = 0; i < member.length(); i++) {
                        JSONObject c = member.getJSONObject(i);
                        String str = c.getString("in") +":" + c.getString("yearf")+":"+c.getString("yeart");
                        map.add(str);
                        s = false;
                    }
                } catch (final JSONException e) {

                }
            } else {

            }
        }
    }
}