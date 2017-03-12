package com.example.ahsan.emobile.Fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                return new DetailFragment();
            case 1:
                return new MemberFragment();
            case 2:
                return new AddFragment();
            case 3:
                return new RequestFragment();
            case 4:
                return new ChatFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 5;
    }

}