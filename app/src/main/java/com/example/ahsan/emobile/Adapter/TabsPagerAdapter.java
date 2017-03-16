package com.example.ahsan.emobile.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.ahsan.emobile.Fragments.AddFragment;
import com.example.ahsan.emobile.Fragments.ChatFragment;
import com.example.ahsan.emobile.Fragments.DetailFragment;
import com.example.ahsan.emobile.Fragments.MemberFragment;
import com.example.ahsan.emobile.Fragments.RequestFragment;

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