package com.example.restaurantreservation.Menu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {
    // page Adapter for Menu Fragment
    private int numofTabs;
    public PageAdapter(@NonNull FragmentManager fm, int numofTabs) {
        super(fm);
        this.numofTabs=numofTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new tab1();
            case 1:
                return new tab2();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numofTabs;
    }
    public int getItemPosition(@Nullable Object object){
        return POSITION_NONE;
    }

}
