package com.example.restaurantreservation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    List<RestaurantCard> listRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDb = new DatabaseHelper(this);
//        myDb.deleteAllTables();
        myDb.insertData();
        myDb.insertFoodDrinks();
        myDb.insertMenu();
        setContentView(R.layout.activity_main);

        Button buttonOne = findViewById(R.id.buttonOne);
        buttonOne.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Button Clicked");

                Intent mapActivity = new Intent(getApplicationContext(), RestaurantHomePage.class);
                startActivity(mapActivity);
            }
        });
        Button button=findViewById(R.id.buttonLogin);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Button Clicked");
                Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginActivity);
            }
        });

//        listRestaurant =new ArrayList<>();
//        listRestaurant.add(new RestaurantCard("People","muie",R.drawable.res1));
//        listRestaurant.add(new RestaurantCard("Napoli Centrale","muie",R.drawable.res2));
//        listRestaurant.add(new RestaurantCard("Cimbru","muie",R.drawable.res3));
//        listRestaurant.add(new RestaurantCard("Kfc","muie",R.drawable.res4));
//        listRestaurant.add(new RestaurantCard("Mc","muie",R.drawable.res5));
//        listRestaurant.add(new RestaurantCard("People","muie",R.drawable.res1));
//        listRestaurant.add(new RestaurantCard("Napoli Centrale","muie",R.drawable.res2));
//        listRestaurant.add(new RestaurantCard("Cimbru","muie",R.drawable.res3));
//        listRestaurant.add(new RestaurantCard("Kfc","muie",R.drawable.res4));
//        listRestaurant.add(new RestaurantCard("Mc","muie",R.drawable.res5));
//
//        RecyclerView recyclerView=(RecyclerView) findViewById(R.id.recyclerview_id);
//        RecyclerViewAdapter adapter=new RecyclerViewAdapter(this,listRestaurant);
//        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
//        recyclerView.setAdapter(adapter);
    }
}