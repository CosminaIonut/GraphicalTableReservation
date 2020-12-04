package com.example.restaurantreservation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PageAdapter2 extends FragmentPagerAdapter {
    private int numofTabs;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private List<String> mFragmentTitleList = new ArrayList<>();
    public PageAdapter2(@NonNull FragmentManager fm, int numofTabs) {
        super(fm);
        this.numofTabs=numofTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }
    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public int getCount() {
        return numofTabs;
    }
    public int getItemPosition(@Nullable Object object){
        return POSITION_NONE;
    }

}
