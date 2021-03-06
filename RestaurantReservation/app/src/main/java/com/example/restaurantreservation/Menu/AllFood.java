package com.example.restaurantreservation.Menu;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.restaurantreservation.DatabaseHelper;
import com.example.restaurantreservation.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllFood#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllFood extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int position=0;
    DatabaseHelper myDb;
    ArrayList<String> item_id,item_name, item_desc, item_price , item_quantity, item_sortiment;
    RecyclerView recyclerView;
    CustomAdapter customAdapter;
    private TabLayout tabLayout;



    public AllFood() {
        // Required empty public constructor
    }
    public AllFood(int pos){
        position=pos;

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment tab3.
     */
    public static AllFood newInstance(String param1, String param2) {
        AllFood fragment = new AllFood();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        item_id = new ArrayList<>();
        item_name = new ArrayList<>();
        item_desc = new ArrayList<>();
        item_quantity = new ArrayList<>();
        item_price = new ArrayList<>();
        item_sortiment = new ArrayList<>();
        myDb = new DatabaseHelper(getContext());
        Bundle extras =getActivity().getIntent().getExtras();
        int id = Integer.parseInt(extras.getString("RestaurantID" +""));
        storeDataInArray(id);

        View view = inflater.inflate(R.layout.fragment_allfood, container, false);
        recyclerView = view.findViewById(R.id.menuList);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayoutFoodSection);

        RecyclerView.LayoutManager recyce = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(recyce);
        customAdapter = new CustomAdapter(getContext(), item_id, item_name, item_desc, item_price, item_quantity, item_sortiment);
        recyclerView.setAdapter(customAdapter);
        return view;

    }
    public void storeDataInArray(int restaurantId ) {

        Cursor result =myDb.getallFoodFromMenu(restaurantId);;
        if(position==0){ 
            result = myDb.getallFoodFromMenu(restaurantId);
        }else if(position==1){
            result=myDb.getallFoodSortiment(restaurantId,"Starters");

        }else if(position==2){
            result=myDb.getallFoodSortiment(restaurantId,"Salads");

        }else if(position==3){
            result=myDb.getallFoodSortiment(restaurantId,"Pasta");

        }else if(position==4) {
            result = myDb.getallFoodSortiment(restaurantId, "Brunch");
        }else if(position==5) {
            result = myDb.getallFoodSortiment(restaurantId, "Main Courses");
        }else if(position==6) {
            result = myDb.getallFoodSortiment(restaurantId, "Urban Goodies");
        }else if(position==7) {
            result = myDb.getallFoodSortiment(restaurantId, "Sides");
        }else if(position==8 ) {
            result = myDb.getallFoodSortiment(restaurantId, "Desserts");
        }

        if (result.getCount() == 0) {
            showMessage("Error", "Keine Tupel in der Tabelle");

        } else {
            while (result.moveToNext()) {

                String itemID = result.getString(2);
                String itemName = result.getString(3);
                String itemDescription = result.getString(4);
                String price = result.getString(5);
                String quantity = result.getString(6);
                String type = result.getString(7);
                String sortiment = result.getString(8);
                item_id.add(itemID);
                item_name.add(itemName);
                item_desc.add(itemDescription);
                item_price.add(price);
                item_sortiment.add(sortiment);
                item_quantity.add(quantity);

            }
        }
    }
    public void showMessage(String title, String  message){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder. setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}