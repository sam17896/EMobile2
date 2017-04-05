package com.example.ahsan.emobile.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.ahsan.emobile.Fragments.ContactFragment;
import com.example.ahsan.emobile.Fragments.EducationFragment;
import com.example.ahsan.emobile.Fragments.FriendFragment;
import com.example.ahsan.emobile.Fragments.GroupFragment;
import com.example.ahsan.emobile.Fragments.InterestFragment;
import com.example.ahsan.emobile.Fragments.PersonalInformationFragment;
import com.example.ahsan.emobile.Fragments.SkillsFragment;
import com.example.ahsan.emobile.Fragments.SocialLinksFragment;
import com.example.ahsan.emobile.Fragments.WorkFragment;

public class ProfilePagerAdapter extends FragmentPagerAdapter {

    public ProfilePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                return new PersonalInformationFragment();
            case 1:
                return new FriendFragment();
            case 2:
                return new GroupFragment();
            case 3:
                return new SocialLinksFragment();
            case 4:
                return new ContactFragment();
            case 5:
                return new EducationFragment();
            case 6:
                return new WorkFragment();
            case 7:
                return new SkillsFragment();
            case 8:
                return new InterestFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 9;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Personal Information";
            case 1:
                return "Friends";
            case 2:
                return "Groups";
            case 3:
                return "Social Links";
            case 4:
                return "Contact";
            case 5:
                return "Education";
            case 6:
                return "Work";
            case 7:
                return "Skills";
            case 8:
                return "Interests";
        }
        return null;
    }

}