package com.example.restaurantreservation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurantreservation.DatabaseHelper;
import com.example.restaurantreservation.LoginActivity;
import com.example.restaurantreservation.R;
import com.example.restaurantreservation.RecyclerViewAdapter;
import com.example.restaurantreservation.RestaurantCard;
import com.example.restaurantreservation.RestaurantHomePage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    ArrayList<String> restaurant_id= new ArrayList<>();
    ArrayList<String> restaurant_name= new ArrayList<>();
    ArrayList<String> restaurant_adress= new ArrayList<>();
    ArrayList<Integer> restaurant_thumnail = new ArrayList<>();;
    String photoString="rest";
    EditText searchBar;
    TextView helloText;
    ImageView imageLogin;
    List<RestaurantCard> listRestaurant;
    public static Activity main;

    /*
    * Adds restaurant data from db to the info arrays.
    * */
    public void storeDataInArray(String searchText){
        restaurant_adress.clear();
        restaurant_id.clear();
        restaurant_name.clear();
        restaurant_thumnail.clear();
        Cursor result;

        if(searchText.length()==0){
         result= myDb.getAllRestaurants();
        }else{
            result= myDb.getSearchRestaurant(searchText);
        }

        if(result.getCount()==0){

        }else{
            while (result.moveToNext()){
                photoString="rest";
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

    private static boolean doesDatabaseExist(Context context, String dbName) {
        try{
            File dbFile = context.getDatabasePath(dbName);
            return dbFile.exists();
        }
        catch(Exception e){
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main = this;

        boolean DBexist = doesDatabaseExist(this, "RestaurantApp.db");

        if(!DBexist){
            // onCreate() -> creates the database and the tables
            myDb = new DatabaseHelper(this);

            // add the restaurants to the db
            myDb.insertMoarRestaurants();

            // bind the maps to the restaurant id, mandatory before insertData()
            myDb.insertMapToRestaurant();

            // add the tables coordinates to the db
            myDb.insertData();

            // populate the food and drinks table
            myDb.insertFoodDrinks();

            // bind consumables ids to the menu of the restaurants
            myDb.insertMenu();
        }
        else{
            myDb = new DatabaseHelper(this);
        }

        setContentView(R.layout.activity_main);
        helloText= findViewById(R.id.textUsername);
        imageLogin= findViewById(R.id.imageMenu);
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String pref_email = preferences.getString("pref_USERNAME", "");
        String name = "";

        if (pref_email.equals("")){
            String mdrawable="loginbtn";
            final int resID = getResources().getIdentifier(mdrawable , "drawable", getPackageName());
            imageLogin.setImageResource(resID);
            imageLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(loginActivity);
                }
            });

        }else{

            String mdrawable="logoutbtn";
            final int resID = getResources().getIdentifier(mdrawable , "drawable", getPackageName());

            name= myDb.getName(pref_email);
            SharedPreferences.Editor edit= preferences.edit();
            edit.putString("pref_NAME",name);
            edit.commit();
            imageLogin.setImageResource(resID);
            imageLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences.Editor edit= preferences.edit();
                    edit.putString("pref_USERNAME","");
                    edit.commit();
                    finish();
                    startActivity(getIntent());
                }
            });


        }

        helloText.setText(name);
        searchBar=findViewById(R.id.search);

        final Cursor result = myDb.getAllRestaurants();

        storeDataInArray("");
        final RecyclerView recyclerView=(RecyclerView) findViewById(R.id.recyclerview_id);
        RecyclerViewAdapter adapter=new RecyclerViewAdapter(this,restaurant_id,restaurant_name,restaurant_adress, restaurant_thumnail);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(adapter);

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    storeDataInArray(charSequence.toString());
                    RecyclerViewAdapter  adapter2=new RecyclerViewAdapter(getBaseContext(),restaurant_id,restaurant_name,restaurant_adress, restaurant_thumnail);
                    recyclerView.setAdapter(adapter2);
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

    }
}