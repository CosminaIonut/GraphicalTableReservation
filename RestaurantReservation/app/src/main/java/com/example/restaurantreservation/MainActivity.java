package com.example.restaurantreservation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
    EditText searchBar;
    List<RestaurantCard> listRestaurant;

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

    public void showMessage(String title, String  message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
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
//        myDb.deleteAllRestaurants();
//        myDb.insertMoarRestaurants();

        // restaurant 2 stuff
        //myDb.insertData();
        //myDb.insertMapToRestaurant();

        setContentView(R.layout.activity_main);
        searchBar=findViewById(R.id.search);
        final Cursor result = myDb.getAllRestaurants();
        Button button=findViewById(R.id.buttonLogin);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Button Clicked");
                Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginActivity);
            }
        });
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