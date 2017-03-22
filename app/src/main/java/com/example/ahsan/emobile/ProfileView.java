package com.example.ahsan.emobile;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ahsan.emobile.Adapter.ProfilePagerAdapter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by AHSAN on 3/12/2017.
 */

public class ProfileView extends ActionBarActivity implements ActionBar.TabListener, ViewPager.OnPageChangeListener {

    private ViewPager viewPager;
    private ProfilePagerAdapter mAdapter;
    private ActionBar actionBar;
    SessionManager session;
    private String[] tabs = { "Personal Information", "Friends", "Groups", "Social Links", "Contact", "Education", "Work" , "Skills", "Interest"};
    public  String[] tabsAdd = {};
    int status;
    Menu menu;
    MenuItem item,item2,item3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);


        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getSupportActionBar();
        mAdapter = new ProfilePagerAdapter(getSupportFragmentManager());
        session = new SessionManager(getApplicationContext());
        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        addTabs();

        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009688")));

        viewPager.setOnPageChangeListener(this);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        String name = item.getTitle().toString();

        if (id == R.id.action_edit) {
            Intent i = new Intent(ProfileView.this, EditActivity.class);
            startActivity(i);
            return true;
        }
        if(id == android.R.id.home){
            this.finish();
            return true;
        }

        if(id == R.id.action_message){

            return true;
        }

        switch(name){
            case "Add":
                myTask2 add = new myTask2();
                add.execute();
                break;

            case "Reject":
                myTask4 reject = new myTask4();
                reject.execute();

                break;

            case "Cancel":
                myTask5 cancel = new myTask5();
                cancel.execute();
                break;

            case "Approve":
                myTask3 approve = new myTask3();
                approve.execute();
                break;

            case "Remove":
                myTask1 remove = new myTask1();
                remove.execute();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.profile_menu, m);
        MenuItem item1;
        if(session.getUserID().equals(session.getProfile())){
            item1 = m.findItem(R.id.action_add);
            item1.setVisible(false);
            item1 = m.findItem(R.id.action_remove);
            item1.setVisible(false);
            item3 = m.findItem(R.id.action_message);
            item3.setVisible(false);
        }
        else {
            item = m.findItem(R.id.action_add);
            item2 = m.findItem(R.id.action_remove);
            item1 = m.findItem(R.id.action_edit);
            item1.setVisible(false);
            update();
        }


        return true;
    }



    public void addTabs(){

        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
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
    public void update(){
        myTask task = new myTask();
        task.execute();
    }

    private class myTask extends AsyncTask<String, String , String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            MenuItem item1;
            switch (status){
                case 0:
                    item.setTitle("Remove");
                    item.setIcon(R.drawable.icon_remove);
                    item2.setVisible(false);
                    break;
                case 1:
                    item.setTitle("Cancel");
                    item.setIcon(R.drawable.icon_reject);
                    item2.setVisible(false);
                    break;
                case 2:
                    item.setTitle("Approve");
                    item.setIcon(R.drawable.icon_added);
                    item2.setVisible(true);
                    item2.setTitle("Reject");
                    item2.setIcon(R.drawable.icon_reject);
                    break;
                case 3:
                    item.setTitle("Add");
                    item.setIcon(R.drawable.add_icon);
                    item2.setVisible(false);
                    break;



            }
        }

        @Override
        protected String doInBackground(String... params) {

            HttpHandler sh = new HttpHandler();
            String url = AppConfig.URL + "friendstatus.php?id=" + session.getUserID() + "&fid=" + session.getProfile();
            String response = sh.makeServiceCall(url);

            if (response != null) {
                try {
                    JSONObject jsonObj = new JSONObject(response);
                    status = jsonObj.getInt("status");

                } catch (final JSONException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                }
            }

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
            update();
        }

        @Override
        protected String doInBackground(String... params) {

            HttpHandler sh = new HttpHandler();
            String url = AppConfig.URL + "remove_friend.php?id=" + session.getUserID() + "&fid=" + session.getProfile();
            sh.makeServiceCall(url);

            return null;
        }

    }

    private class myTask2 extends AsyncTask<String, String , String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            update();
        }

        @Override
        protected String doInBackground(String... params) {

            HttpHandler sh = new HttpHandler();
            String url = AppConfig.URL + "add_friend.php?sid=" + session.getUserID() + "&rid=" + session.getProfile();
            sh.makeServiceCall(url);

            return null;
        }

    }

    private class myTask3 extends AsyncTask<String, String , String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            update();
        }

        @Override
        protected String doInBackground(String... params) {

            HttpHandler sh = new HttpHandler();
            String url = AppConfig.URL + "accept_request.php?rid=" + session.getUserID() + "&sid=" + session.getProfile();
            sh.makeServiceCall(url);

            return null;
        }

    }

    private class myTask4 extends AsyncTask<String, String , String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            update();
        }

        @Override
        protected String doInBackground(String... params) {

            HttpHandler sh = new HttpHandler();
            String url = AppConfig.URL + "reject_request.php?rid=" + session.getUserID() + "&sid=" + session.getProfile();
            sh.makeServiceCall(url);

            return null;
        }

    }

    private class myTask5 extends AsyncTask<String, String , String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            update();
        }

        @Override
        protected String doInBackground(String... params) {

            HttpHandler sh = new HttpHandler();
            String url = AppConfig.URL + "reject_request.php?sid=" + session.getUserID() + "&rid=" + session.getProfile();
            sh.makeServiceCall(url);

            return null;
        }

    }


}
