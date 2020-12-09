package com.example.restaurantreservation;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

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

//        return super.onCreateView(inflater, container, savedInstanceState);
          final View view = inflater.inflate(R.layout.menu_fragemnt, container, false);
          btnNavFrgMenu= view.findViewById(R.id.menu_fragment);
          btnNavFrgRestaurant= view.findViewById(R.id.restaurant_fragment);
          tabLayout= view.findViewById(R.id.tabLayout);
          food= view.findViewById(R.id.food);
          drinks =view.findViewById(R.id.drinks);
          viewPager=view.findViewById(R.id.menu_viewPager);
          pagerAdapter= new PageAdapter(getFragmentManager(),tabLayout.getTabCount());
          viewPager.setAdapter(pagerAdapter);
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
          viewPager.addOnPageChangeListener( new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

           btnNavFrgMenu.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
//                  Toast.makeText(getActivity(),"Going to Frg Menu", Toast.LENGTH_SHORT).show();
                  ((RestaurantHomePage)getActivity()).setViewPager(1);
              }
          });
        btnNavFrgRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getActivity(),"Going to Frg Restaurnat", Toast.LENGTH_SHORT).show();
                ((RestaurantHomePage)getActivity()).setViewPager(0);
            }
        });

          return view;
    }




}
