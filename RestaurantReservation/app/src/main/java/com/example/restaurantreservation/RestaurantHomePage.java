package com.example.restaurantreservation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RestaurantHomePage extends AppCompatActivity {
    private static final int BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT =1;
    private SectionStatePagerAdapter mSectionStatePagerAdapter;
    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_home_page);
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        Button buttonOne = findViewById(R.id.buttonBookingDetails);
        mSectionStatePagerAdapter = new SectionStatePagerAdapter(getSupportFragmentManager(),BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewPager= findViewById(R.id.container);
        setupViewPager(mViewPager);
        buttonOne.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mapActivity = new Intent(getApplicationContext(), Booking.class);
                startActivity(mapActivity);
            }
        });
    }
    private void setupViewPager(ViewPager viewPager){
        SectionStatePagerAdapter adapter= new SectionStatePagerAdapter(getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragemnt(new MenuFragment(), "MenuFragemnt");
        adapter.addFragemnt(new RestaurantFragment(), "RestaurantFragemnt");
        viewPager.setAdapter(adapter);
    }
    public  void setViewPager(int fragemntNumber){
        mViewPager.setCurrentItem(fragemntNumber);
    }



}