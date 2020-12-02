package com.example.restaurantreservation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageAdapter3 extends FragmentPagerAdapter {
    private int numofTabs;
    public PageAdapter3(@NonNull FragmentManager fm, int numofTabs) {
        super(fm);
        this.numofTabs=numofTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return new AllDrinkTab(position);
    }

    @Override
    public int getCount() {
        return numofTabs;
    }
    public int getItemPosition(@Nullable Object object){
        return POSITION_NONE;
    }

}
