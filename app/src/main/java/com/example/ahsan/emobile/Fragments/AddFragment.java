package com.example.ahsan.emobile.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ahsan.emobile.Adapter.AddAdapter;
import com.example.ahsan.emobile.AppConfig;
import com.example.ahsan.emobile.HttpHandler;
import com.example.ahsan.emobile.R;
import com.example.ahsan.emobile.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddFragment extends Fragment {

    ListView lv;
    ArrayList<String> map;
    boolean s;
    SessionManager session;
    String jsonStr;
    SwipeRefreshLayout srl;
    boolean refresh = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_add, container, false);


        session = new SessionManager(getContext().getApplicationContext());
        lv = (ListView) rootView.findViewById(R.id.add);

        map = new ArrayList<>();

        srl = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh);
        setHasOptionsMenu(true);

        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                map = new ArrayList<String>();
                MyTask task = new MyTask();
                task.execute();
                refresh = true;
            }
        });

        MyTask task = new MyTask();
        task.execute();

        return rootView;
    }
    private void showMessage(String s) {
        Toast.makeText(getContext().getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

    public class MyTask extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... s) {

            HttpHandler sh = new HttpHandler();

            String url = AppConfig.URL +"loadfriend.php?id=" + session.getTopicID() + "&uid="+ session.getUserID();
            LoadMember(url, sh);
            return null;
        }

        @Override
        protected void onPostExecute(String str) {
            super.onPostExecute(str);
            if(refresh){
                refresh = false;
                srl.setRefreshing(false);
            }


            ListAdapter adapter = new AddAdapter(getActivity(),R.layout.add, map ,s);
            lv.setAdapter(adapter);


        }

        @Override
        protected void onProgressUpdate(String... values) {
        }

        public void LoadMember(String url, HttpHandler sh){
            jsonStr = sh.makeServiceCall(url);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    JSONArray member = jsonObj.getJSONArray("friend");

                    for (int i = 0; i < member.length(); i++) {
                        JSONObject c = member.getJSONObject(i);
                        String str = c.getString("username") +":" + c.getString("userid")+":"+c.getString("pic");
                        map.add(str);
                        s = c.getBoolean("s");
                    }
                } catch (final JSONException e) {

                }
            } else {

            }
        }
    }
}