package com.example.restaurantreservation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RestaurantHomePage extends AppCompatActivity {
    private static final int BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT =1;
    private SectionStatePagerAdapter mSectionStatePagerAdapter;
    private ViewPager mViewPager;
    private TextView restTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_home_page);

        Bundle extras = getIntent().getExtras();
        int id = Integer.parseInt(extras.getString("RestaurantID"));
        String name = extras.getString("RestaurantName");

        getWindow().getDecorView().setBackgroundColor(Color.WHITE);

        // Add restaurant title.
        restTitle = findViewById(R.id.resTitle);
        restTitle.setText(name);

        // Fragment pager declaration and init with restaurant home page
        mSectionStatePagerAdapter = new SectionStatePagerAdapter(getSupportFragmentManager(),BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewPager= findViewById(R.id.container);
        setupViewPager(mViewPager);
    }

    /*
    * Initialise the ViewPager with the fragments.
    * Add order changes view order btw.
    */
    private void setupViewPager(ViewPager viewPager){
        SectionStatePagerAdapter adapter= new SectionStatePagerAdapter(getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragemnt(new RestaurantFragment(), "RestaurantFragemnt");
        adapter.addFragemnt(new MenuFragment(), "MenuFragemnt");
        viewPager.setAdapter(adapter);
    }

    /*
    * Changes the currently shown fragment in the ViewPager.
    * */
    public  void setViewPager(int fragemntNumber){
        mViewPager.setCurrentItem(fragemntNumber);
    }



}