package com.example.ahsan.emobile;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ahsan.emobile.Adapter.TabsPagerAdapter;
import com.example.ahsan.emobile.Adapter.TopicMemberAdapter;
import com.example.ahsan.emobile.Adapter.TopicNMemberAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class TopicView extends AppCompatActivity {

    public  String[] tabsmember = {"Details" , "Members", "Chat"};
    public String[] tabsnmember = {"Details", "Members"};
    SessionManager session;
    MenuItem item1;
    SwipeRefreshLayout srl;
    boolean refresh = false;
    private ViewPager viewPager;
    private ActionBar actionBar;
    private String[] tabadmin = {"Details", "Members", "Add", "Request", "Chat"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic);

        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getSupportActionBar();
        session = new SessionManager(getApplicationContext());
        actionBar.setTitle(session.getTopicName());
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));

        update();

    }

    private void update() {
        MyTask1 task = new MyTask1();
        task.execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.topic_menu, m);
        return true;
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        String name = item.getTitle().toString();

        if(id == android.R.id.home){
            this.finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public class MyTask1 extends AsyncTask<String, String, String> {

        int status;
        boolean admin;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String URL = AppConfig.URL + "groupstatus.php?id=" + session.getUserID() + "&tid=" + session.getTopicID();
            HttpHandler sh = new HttpHandler();
            String response = sh.makeServiceCall(URL);

            if(response != null){
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray array = jsonObject.getJSONArray("status");

                    for(int i=0;i<array.length();i++){
                        JSONObject js = array.getJSONObject(i);
                        status = js.getInt("status");
                        admin = js.getBoolean("admin");
                    }

                    Log.d("Group Status", "" + status + " " + admin);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

         return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(refresh){
                refresh = false;
                srl.setRefreshing(false);
            }
            if(admin) {
                TabsPagerAdapter mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
                viewPager.setAdapter(mAdapter);
            }

            if(status == 0 && !admin){
                TopicMemberAdapter mAdapter = new TopicMemberAdapter(getSupportFragmentManager());
                viewPager.setAdapter(mAdapter);
            }

            if(status > 0){
                TopicNMemberAdapter mAdapter = new TopicNMemberAdapter(getSupportFragmentManager());
                viewPager.setAdapter(mAdapter);
            }

            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(viewPager);

            actionBar.setHomeButtonEnabled(true);
        }
    }

}
