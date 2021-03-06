package com.example.restaurantreservation.RestaurantHomePage;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SectionStatePagerAdapter extends FragmentStatePagerAdapter {
    // Page adapter for the Restaurant Home Page Fragment
    private final List<Fragment> mFragmentList= new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    public SectionStatePagerAdapter(FragmentManager fm, int behavior) {
     super (fm ,behavior);

    }
    public void addFragemnt(Fragment fragment, String title){
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
