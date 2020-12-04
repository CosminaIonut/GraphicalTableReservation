package com.example.restaurantreservation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class RestaurantFragment extends Fragment {
    private static final String TAG="MenuFragment";
    private ImageView btnNavFrgMenu;
    private ImageView btnNavFrgRestaurant;
    private ImageView btnNavBookingActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
          View view = inflater.inflate(R.layout.restaurant_fragment, container, false);
          btnNavFrgMenu= view.findViewById(R.id.menu_fragment);
          btnNavFrgRestaurant= view.findViewById(R.id.restaurant_fragment);
          btnNavBookingActivity=view.findViewById(R.id.buttonBookingDetails);

          btnNavFrgMenu.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  ((RestaurantHomePage)getActivity()).setViewPager(0);
              }
          });

          btnNavFrgRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((RestaurantHomePage)getActivity()).setViewPager(1);
            }
          });

          btnNavBookingActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity() , Booking.class);
                startActivity(intent);
            }
          });

          return view;
    }
}
