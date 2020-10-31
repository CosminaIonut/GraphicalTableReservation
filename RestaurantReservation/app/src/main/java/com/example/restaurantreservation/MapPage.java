package com.example.restaurantreservation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MapPage extends AppCompatActivity {
    DatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int x=0;
        int y=0;
        setContentView(R.layout.activity_map_page);
        myDb = new DatabaseHelper(this);
        myDb.getAllData();
        Cursor result = myDb.getAllData();
        if (result.getCount() == 0){
//            showMessage("Error", "Keine Tupel in der Tabelle");
            return;
        }else {
            //showMessage("Error", result.getString(0));
            result.moveToNext();
            x = Integer.parseInt(result.getString(0));
            y = Integer.parseInt(result.getString(1));
        }


//        System.out.println(x);
//        String context="src/main/res/drawable-v24/table.png";
        RelativeLayout mRelativeLayout = (RelativeLayout)findViewById(R.id.wrapper);
        ImageView tv = new ImageView(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        String mDrawableName = "table";
        int resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
        tv.setImageResource(resID);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        params.leftMargin = x;
        mRelativeLayout.addView(tv, params);
//        addContentView(tv,params);
    }
    public void showMessage(String title, String  message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder. setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
