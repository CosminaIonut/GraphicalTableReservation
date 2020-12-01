package com.example.restaurantreservation;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link tab2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class tab2 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    CustomAdapter customAdapter;
    DatabaseHelper myDb;
    ArrayList<String> item_id,item_name, item_desc, item_price , item_quantity, item_sortiment;
    RecyclerView recyclerView;
    private NonSwipeableViewPager viewPager;
    private TabLayout tabLayout;
    public PageAdapter3 pagerAdapter;
    HashSet<String> hashSet= new HashSet<>();


    public tab2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment tab2.
     */
    // TODO: Rename and change types and number of parameters
    public static tab2 newInstance(String param1, String param2) {
        tab2 fragment = new tab2();
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
        View view = inflater.inflate(R.layout.fragment_tab2, container, false);
        tabLayout= view.findViewById(R.id.tabLayoutDrinkSection);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        myDb = new DatabaseHelper(getContext());

        getDrinkSortiments();
        viewPager=view.findViewById(R.id.menu_viewPagerDrink);
        for (String s:hashSet){
            System.out.println(s);
            TabLayout.Tab firstTab = tabLayout.newTab(); // Create a new Tab names "First Tab"
            firstTab.setText(s); // set the Text for the first Tab// set an icon for the first tab
            tabLayout.addTab(firstTab);
        }

        System.out.println(tabLayout.getTabCount());
        pagerAdapter= new PageAdapter3(getFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition()==0){
                    pagerAdapter.notifyDataSetChanged();
                }else if(tab.getPosition()==1){
                    pagerAdapter.notifyDataSetChanged();
                }else if(tab.getPosition()==2){
                    pagerAdapter.notifyDataSetChanged();
                }else if(tab.getPosition()==3){
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



//        recyclerView=view.findViewById(R.id.menuList);
////          listView=view.findViewById(R.id.menuList);
//        myDb = new DatabaseHelper(getContext());
//        item_id=new ArrayList<>();
//        item_name=new ArrayList<>();
//        item_desc=new ArrayList<>();
//        item_quantity=new ArrayList<>();
//        item_price=new ArrayList<>();
//        item_sortiment=new ArrayList<>();
//        storeDataInArray();
//        RecyclerView.LayoutManager recyce = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
//        recyclerView.setLayoutManager(recyce);
//        customAdapter=new CustomAdapter(getContext(),item_id,item_name,item_desc,item_price,item_quantity,item_sortiment);
//        recyclerView.setAdapter(customAdapter);
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
        Cursor result = myDb.getallDrinksFromMenu(restaurantId);
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
    public void getDrinkSortiments(){
        Cursor result = myDb.getallDrinksSortimentsFromMenu();
        if (result.getCount() == 0) {
            showMessage("Error", "Keine Tupel in der Tabelle");

        } else {
            hashSet.add("All");
            while (result.moveToNext()) {

                String sortiment = result.getString(0);
                hashSet.add(sortiment);


            }
        }
    }
}