package com.example.ahsan.emobile.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.ahsan.emobile.Fragments.ChatFragment;
import com.example.ahsan.emobile.Fragments.DetailFragment;
import com.example.ahsan.emobile.Fragments.MemberFragment;

public class TopicMemberAdapter extends FragmentPagerAdapter {

    public TopicMemberAdapter(FragmentManager fm) {
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
                return new ChatFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Details";
            case 1:
                return "Members";
            case 2:
                return "Discussion";
        }
        return null;
    }

}