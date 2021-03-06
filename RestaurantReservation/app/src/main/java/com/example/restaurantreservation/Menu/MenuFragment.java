package com.example.restaurantreservation.Menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.viewpager.widget.ViewPager;

import com.example.restaurantreservation.R;
import com.example.restaurantreservation.RestaurantHomePage.RestaurantHomePage;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class MenuFragment extends Fragment {
    private static final String TAG="MenuFragment";
    private ImageView btnNavFrgMenu;
    private ImageView btnNavFrgRestaurant;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabItem drinks, food;
    public PageAdapter pagerAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

          final View view = inflater.inflate(R.layout.menu_fragemnt, container, false);
          btnNavFrgMenu= view.findViewById(R.id.menu_fragment);
          btnNavFrgRestaurant= view.findViewById(R.id.restaurant_fragment);
          tabLayout= view.findViewById(R.id.tabLayout);
          food= view.findViewById(R.id.food);
          drinks =view.findViewById(R.id.drinks);
          viewPager=view.findViewById(R.id.menu_viewPager);
          pagerAdapter= new PageAdapter(getFragmentManager(),tabLayout.getTabCount());
          viewPager.setAdapter(pagerAdapter);
          // Tableyout with food an drinks tabs
          tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
              @Override
              public void onTabSelected(TabLayout.Tab tab) {
                  viewPager.setCurrentItem(tab.getPosition());
                  if(tab.getPosition()==0){
                      pagerAdapter.notifyDataSetChanged();
                  }else if(tab.getPosition()==1){
                      pagerAdapter.notifyDataSetChanged();
                  }
              }

              @Override
              public void onTabUnselected(TabLayout.Tab tab) {

              }

              @Override
              public void onTabReselected(TabLayout.Tab tab) {

              }
          });
          // move to map Home Fragment or to Menu Fragemnt
          viewPager.addOnPageChangeListener( new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
          btnNavFrgMenu.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  ((RestaurantHomePage)getActivity()).setViewPager(1);
              }
          });
         btnNavFrgRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((RestaurantHomePage)getActivity()).setViewPager(0);
            }
        });

          return view;
    }




}
