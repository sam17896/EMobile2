package com.example.ahsan.emobile;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;

import com.example.ahsan.emobile.Adapter.ProfilePagerAdapter;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);


        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getSupportActionBar();
        mAdapter = new ProfilePagerAdapter(getSupportFragmentManager());
        session = new SessionManager(getApplicationContext());
        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        addTabs();

        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009688")));

        viewPager.setOnPageChangeListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if(session.getUserID()==session.getProfile())
        getMenuInflater().inflate(R.menu.profile_menu, menu);
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
}
