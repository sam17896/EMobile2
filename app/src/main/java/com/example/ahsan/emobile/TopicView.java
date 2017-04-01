package com.example.ahsan.emobile;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ahsan.emobile.Adapter.TabsPagerAdapter;
import com.example.ahsan.emobile.Adapter.TopicMemberAdapter;
import com.example.ahsan.emobile.Adapter.TopicNMemberAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by AHSAN on 3/12/2017.
 */

public class TopicView extends ActionBarActivity implements ActionBar.TabListener, ViewPager.OnPageChangeListener  {

    private ViewPager viewPager;
    private ActionBar actionBar;
    SessionManager session;
    private String[] tabadmin = { "Details", "Members", "Add", "Request", "Chat" };
    public  String[] tabsmember = {"Details" , "Members", "Chat"};
    public String[] tabsnmember = {"Details", "Members"};
    MenuItem item1;
    SwipeRefreshLayout srl;
    boolean refresh = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic);

        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getSupportActionBar();
        session = new SessionManager(getApplicationContext());

         update();


    }
    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.topic_menu, m);
        item1 = m.findItem(R.id.action);
        return true;
    }

    public void update(){
        MyTask task = new MyTask();
        task.execute();
    }


    public void addTabs(int status){
        switch (status){
            case 0:
                for (String tab_name : tabadmin) {
                    actionBar.addTab(actionBar.newTab().setText(tab_name)
                            .setTabListener(this));
                }
                break;
            case 1:
                for (String tab_name : tabsmember) {
                    actionBar.addTab(actionBar.newTab().setText(tab_name)
                            .setTabListener(this));
                }
                break;
            case 2:
                for (String tab_name : tabsnmember) {
                    actionBar.addTab(actionBar.newTab().setText(tab_name)
                            .setTabListener(this));
                }
                break;
        }


    }
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        actionBar.setSelectedNavigationItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        String name = item.getTitle().toString();

        if(id == android.R.id.home){
            this.finish();
            return true;
        }


        switch(name){
            case "Leave":
                left l = new left();
                l.execute();
                break;
            case "Join":
                request r = new request();
                r.execute();
                break;

            case "Cancel":
                cancel c = new cancel();
                c.execute();

                break;

        }

        return super.onOptionsItemSelected(item);
    }

    public class MyTask extends AsyncTask<String,String,String> {

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

            switch(status){
                case 0:
                    item1.setTitle("Leave");
                    item1.setIcon(R.drawable.icon_logout);
                    break;
                case 2:
                    item1.setTitle("Join");
                    item1.setIcon(R.drawable.icon_create);
                    break;
                case 1:
                    item1.setTitle("Cancel");
                    item1.setIcon(R.drawable.icon_reject);
                    break;

            }
            actionBar.removeAllTabs();
            if(admin) {
                TabsPagerAdapter mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
                addTabs(0);
                viewPager.setAdapter(mAdapter);
            }

            if(status == 0 && !admin){
                TopicMemberAdapter mAdapter = new TopicMemberAdapter(getSupportFragmentManager());
                addTabs(1);
                viewPager.setAdapter(mAdapter);
            }

            if(status > 0){
                TopicNMemberAdapter mAdapter = new TopicNMemberAdapter(getSupportFragmentManager());
                addTabs(2);
                viewPager.setAdapter(mAdapter);
            }

            actionBar.setHomeButtonEnabled(true);
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009688")));

            viewPager.setOnPageChangeListener(TopicView.this);

        }
    }

    public class cancel extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String url = AppConfig.URL + "cancel.php?id=" + session.getTopicID() + "&uid=" + session.getUserID();
            HttpHandler sh = new HttpHandler();
            sh.makeServiceCall(url);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            update();
        }
    }
    public class left extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String url = AppConfig.URL + "left.php?id=" + session.getTopicID() + "&uid=" + session.getUserID();
            HttpHandler sh = new HttpHandler();
            sh.makeServiceCall(url);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            update();
        }
    }
    public class request extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String url = AppConfig.URL + "request.php?id=" + session.getTopicID() + "&uid=" + session.getUserID();
            HttpHandler sh = new HttpHandler();
            sh.makeServiceCall(url);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            update();
        }
    }
}
