package com.example.restaurantreservation;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class RestaurantFragment extends Fragment {
    private static final String TAG="MenuFragment";
    private ImageView btnNavFrgMenu;
    private ImageView btnNavFrgRestaurant;
    private ImageView btnNavBookingActivity;
    private ImageView map;
    private Dialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
          View view = inflater.inflate(R.layout.restaurant_fragment, container, false);
          map=view.findViewById(R.id.imageView6);
          Bundle extras =getActivity().getIntent().getExtras();
          final int id = Integer.parseInt(extras.getString("RestaurantID"));

          String mdrawable="restaurantmap"+id;
          final int resID = getResources().getIdentifier(mdrawable , "drawable", getActivity().getPackageName());
          map.setImageResource(resID);
          btnNavFrgMenu= view.findViewById(R.id.menu_fragment);
          btnNavFrgRestaurant= view.findViewById(R.id.restaurant_fragment);
          btnNavBookingActivity=view.findViewById(R.id.buttonBookingDetails);

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

          btnNavBookingActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                final String pref_userName = preferences.getString("pref_USERNAME", "");
                if (pref_userName == "") {
                     errorDialog( "In order to book a table you must be logged in first.");
                }else{
                    Intent intent = new Intent(getActivity() , Booking.class);
                    intent.putExtra("RestaurantID",String.valueOf(id));
                    getActivity().finish();
                    startActivity(intent);

                }

            }
          });

          return view;
    }

    public void errorDialog(String message) {
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_booking_login);
        TextView textDialog = dialog.findViewById(R.id.text_dialogLogin);
        textDialog.setText(message);
        dialog.getWindow().setBackgroundDrawable(getActivity().getDrawable(R.drawable.dialog_background));
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        dialog.setCancelable(true);
        Button login=dialog.findViewById(R.id.btn_login);
        Button register= dialog.findViewById(R.id.btn_register);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity() , LoginActivity.class);

                startActivity(intent);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity() , SignUpActivity.class);
                startActivity(intent);
            }
        });
        dialog.show();

    }

}
