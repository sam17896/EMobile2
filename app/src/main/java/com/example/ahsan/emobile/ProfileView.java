package com.example.ahsan.emobile;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ahsan.emobile.Adapter.ProfilePagerAdapter;

public class ProfileView extends AppCompatActivity {

    public  String[] tabsAdd = {};
    SessionManager session;
    int status;
    Menu menu;
    private ViewPager viewPager;
    private ProfilePagerAdapter mAdapter;
    private ActionBar actionBar;
    private String[] tabs = {"Personal Information", "Friends", "Groups", "Social Links", "Contact", "Education", "Work", "Skills", "Interest"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getSupportActionBar();
        mAdapter = new ProfilePagerAdapter(getSupportFragmentManager());
        session = new SessionManager(getApplicationContext());
        viewPager.setAdapter(mAdapter);
        //    actionBar.setHomeAsUpIndicator(R.drawable.icon_back);
        //      actionBar.setDisplayHomeAsUpEnabled(true);
        //     actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle(session.getProfileName());

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        String name = item.getTitle().toString();

        if (id == R.id.action_edit) {
            session.setProfile(session.getUserID());
            Intent i = new Intent(ProfileView.this, EditActivity.class);
            startActivity(i);
            return true;
        }
        if(id == android.R.id.home){
            this.finish();
            return true;
        }

        if(id == R.id.action_message){
            Intent k = new Intent(ProfileView.this, MessageActivity.class);
            startActivity(k);

            return true;
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
            item1 = m.findItem(R.id.action_message);
            item1.setVisible(false);
        }
        else {
            item1 = m.findItem(R.id.action_edit);
            item1.setVisible(false);
        }


        return true;
    }

}
