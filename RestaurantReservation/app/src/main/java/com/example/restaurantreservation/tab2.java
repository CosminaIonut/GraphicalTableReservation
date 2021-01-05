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
import java.util.List;

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
    List<String> list= new ArrayList<>();


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
        for (String s:list){
            System.out.println(s);
            TabLayout.Tab firstTab = tabLayout.newTab();
            firstTab.setText(s);
            tabLayout.addTab(firstTab);
        }

        System.out.println(tabLayout.getTabCount());
        pagerAdapter= new PageAdapter3(getFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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


        return view;
    }


    public void getDrinkSortiments(){
        list= new ArrayList<>();
        Bundle extras =getActivity().getIntent().getExtras();
        int id = Integer.parseInt(extras.getString("RestaurantID"));
        Cursor result = myDb.getallDrinksSortimentsFromMenu(id);
        if (result.getCount() == 0) {

        } else {
            list.add("All");
            while (result.moveToNext()) {

                String sortiment = result.getString(0);
                list.add(sortiment);


            }
        }
    }
}