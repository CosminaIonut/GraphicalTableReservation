package com.example.restaurantreservation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.restaurantreservation.DatabaseHelper;
import com.example.restaurantreservation.LoginActivity;
import com.example.restaurantreservation.R;
import com.example.restaurantreservation.RecyclerViewAdapter;
import com.example.restaurantreservation.RestaurantCard;
import com.example.restaurantreservation.RestaurantHomePage;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    ArrayList<String> restaurant_id= new ArrayList<>();
    ArrayList<String> restaurant_name= new ArrayList<>();
    ArrayList<String> restaurant_adress= new ArrayList<>();
    ArrayList<Integer> restaurant_thumnail = new ArrayList<>();;
    String photoString="rest";
    List<RestaurantCard> listRestaurant;

    public void storeDataInArray(){
        Cursor result=myDb.getAllRestaurants();
        if(result.getCount()==0){
            showMessage("Error", "Keine Tupel in der Tabelle");
        }else{
            while (result.moveToNext()){
                String restId=result.getString(0);
                String restName=result.getString(1);
                String restAdress=result.getString(2);

                restaurant_id.add(restId);
                restaurant_name.add(restName);
                restaurant_adress.add(restAdress);
                photoString+=restId;
                int photoid =getResources().getIdentifier(photoString, "drawable",getPackageName());
                restaurant_thumnail.add(photoid);
            }
        }
    }
    public void showMessage(String title, String  message){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder. setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDb = new DatabaseHelper(this);
//        myDb.deleteAllTables();
//        myDb.insertData();
//        myDb.insertFoodDrinks();
//        myDb.insertMenu();
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
//        listRestaurant.add(new RestaurantCard("People","m",R.drawable.rest1));
//        listRestaurant.add(new RestaurantCard("Napoli Centrale","m",R.drawable.rest2));
//        listRestaurant.add(new RestaurantCard("Cimbru","m",R.drawable.rest3));
//        listRestaurant.add(new RestaurantCard("Kfc","m",R.drawable.rest4));
//        listRestaurant.add(new RestaurantCard("Mc","m",R.drawable.res5));
//        listRestaurant.add(new RestaurantCard("People","m",R.drawable.rest1));
//        listRestaurant.add(new RestaurantCard("Napoli Centrale","m",R.drawable.rest2));
//        listRestaurant.add(new RestaurantCard("Cimbru","m",R.drawable.rest3));
//        listRestaurant.add(new RestaurantCard("Kfc","m",R.drawable.rest4));
//        listRestaurant.add(new RestaurantCard("Mc","m",R.drawable.res5));
        storeDataInArray();

        RecyclerView recyclerView=(RecyclerView) findViewById(R.id.recyclerview_id);
        RecyclerViewAdapter adapter=new RecyclerViewAdapter(this,restaurant_id,restaurant_name,restaurant_adress, restaurant_thumnail);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(adapter);
    }
}