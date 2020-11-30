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
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MenuFragment extends Fragment {
    private static final String TAG="MenuFragment";
    private Button btnNavFrgMenu;
    private Button btnNavFrgRestaurant;
    private Button btnNavBookingActivity;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabItem drinks, food;
    public PageAdapter pagerAdapter;
    CustomAdapter customAdapter;
    DatabaseHelper myDb;
    ListView listView;
    ArrayList<String> item_id,item_name, item_desc, item_price , item_quantity, item_sortiment;
    ArrayAdapter adapter;
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        return super.onCreateView(inflater, container, savedInstanceState);
          final View view = inflater.inflate(R.layout.menu_fragemnt, container, false);
          btnNavFrgMenu= view.findViewById(R.id.menu_fragment);
          btnNavFrgRestaurant= view.findViewById(R.id.restaurant_fragment);
          btnNavBookingActivity=view.findViewById(R.id.buttonBookingDetails);
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
//
////          listItem=new ArrayList<>();
//          recyclerView=view.findViewById(R.id.menuList);
////          listView=view.findViewById(R.id.menuList);
//          myDb = new DatabaseHelper(getContext());
//          item_id=new ArrayList<>();
//          item_name=new ArrayList<>();
//          item_desc=new ArrayList<>();
//          item_quantity=new ArrayList<>();
//          item_price=new ArrayList<>();
//          item_sortiment=new ArrayList<>();
//           storeDataInArray();
//         RecyclerView.LayoutManager recyce = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
//             recyclerView.setLayoutManager(recyce);
//           customAdapter=new CustomAdapter(getContext(),item_id,item_name,item_desc,item_price,item_quantity,item_sortiment);
//           recyclerView.setAdapter(customAdapter);
           btnNavFrgMenu.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  Toast.makeText(getActivity(),"Going to Frg Menu", Toast.LENGTH_SHORT).show();
                  ((RestaurantHomePage)getActivity()).setViewPager(0);
              }
          });
        btnNavFrgRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Going to Frg Restaurnat", Toast.LENGTH_SHORT).show();
                ((RestaurantHomePage)getActivity()).setViewPager(1);
            }
        });
        btnNavBookingActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Going to Frg Menu", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity() , Booking.class);
                startActivity(intent);
            }
        });
          return view;
    }
        public void showMessage(String title, String  message){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder. setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void storeDataInArray() {
        int restaurantId = 1; // acum hardcodat da o sa vina in functie de restaurant
        Cursor result = myDb.getallFoodFromMenu(restaurantId);
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
//                listItem.add(itemName);
                item_id.add(itemID);
                item_name.add(itemName);
                item_desc.add(itemDescription);
                item_price.add(price);
                item_sortiment.add(sortiment);
                item_quantity.add(quantity);

            }
        }
    }


}
