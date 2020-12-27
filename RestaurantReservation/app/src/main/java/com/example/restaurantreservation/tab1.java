package com.example.restaurantreservation;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link tab1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class tab1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    CustomAdapter customAdapter;
    private TabItem t1, t2,t3;

    DatabaseHelper myDb;
    ArrayList<String> item_id,item_name, item_desc, item_price , item_quantity, item_sortiment;
    RecyclerView recyclerView;
    private NonSwipeableViewPager viewPager;
    private TabLayout tabLayout;
    public PageAdapter2 pagerAdapter;
    List<String> list= new ArrayList<>();

    public tab1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment tab1.
     */
    // TODO: Rename and change types and number of parameters
    public static tab1 newInstance(String param1, String param2) {
        tab1 fragment = new tab1();
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

        View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayoutFoodSection);
        myDb = new DatabaseHelper(getContext());

        getFoodSortiments();
        viewPager=view.findViewById(R.id.menu_viewPagerFood);

        for (String s:list){
            System.out.println(s);
            TabLayout.Tab firstTab = tabLayout.newTab();
            firstTab.setText(s);
            tabLayout.addTab(firstTab);
        }
        pagerAdapter= new PageAdapter2(getFragmentManager(),tabLayout.getTabCount());
        int number = tabLayout.getTabCount();
        for (int i=0; i<= number;i++){
                pagerAdapter.addFragment(new AllFood(i),"food");
        }
        viewPager.setAdapter(pagerAdapter);
//        tabLayout.setupWithViewPager(viewPager);

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

    public void getFoodSortiments(){
        list= new ArrayList<>();
        Bundle extras =getActivity().getIntent().getExtras();
        int id = Integer.parseInt(extras.getString("RestaurantID"));
        Cursor result = myDb.getallFoodSortimentsFromMenu(id);
        if (result.getCount() == 0) {
//            showMessage("Error", "Keine Tupel in der Tabelle");

        } else {
            list.add("All");
            while (result.moveToNext()) {

                String sortiment = result.getString(0);
                list.add(sortiment);


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