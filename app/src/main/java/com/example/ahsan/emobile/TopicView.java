package com.example.ahsan.emobile;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import com.example.ahsan.emobile.Adapter.TabsPagerAdapter;

/**
 * Created by AHSAN on 3/12/2017.
 */

public class TopicView extends ActionBarActivity implements ActionBar.TabListener, ViewPager.OnPageChangeListener {

    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    private String[] tabadmin = { "Details", "Members", "Add", "Request", "Chat" };
    public  String[] tabsmember = {"Details" , "Members", "Chat"};
    public String[] tabsnmember = {"Details", "Members"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic);

        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getSupportActionBar();


        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009688")));

        viewPager.setOnPageChangeListener(this);

        // TODO: Implent a method to display the tabs according to the users;

        addTabs(0);

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
                break;
            case 2:
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
}
