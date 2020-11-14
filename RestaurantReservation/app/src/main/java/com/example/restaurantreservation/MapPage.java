package com.example.restaurantreservation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MapPage extends AppCompatActivity {
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int x=0;
        int y=0;
        int id=0;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int Screenheight = displayMetrics.heightPixels;
        int Screenwidth = displayMetrics.widthPixels;
        setContentView(R.layout.activity_map_page);
        myDb = new DatabaseHelper(this);
        myDb.getAllData();
        Cursor result = myDb.getAllData();
        if (result.getCount() == 0){
//            showMessage("Error", "Keine Tupel in der Tabelle");
                return;
        }else {
            while(result.moveToNext()){
                //showMessage("Error", result.getString(0));
                //result.moveToNext();
                x = Integer.parseInt(result.getString(1));
                y = Integer.parseInt(result.getString(2));
                id =Integer.parseInt(result.getString(0));
//            showMessage("Error", String.valueOf(x));

                RelativeLayout mRelativeLayout = (RelativeLayout)findViewById(R.id.wrapper);
                final ImageView tv = new ImageView(this);
                tv.setId(id);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                String mDrawableName = "tablempty";
                int resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
                tv.setImageResource(resID);
                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);

                final ImageView backGroundImage=(ImageView)findViewById(R.id.imageView2);
                RelativeLayout.LayoutParams paramsbg = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                int marginLR= 5*Screenwidth/100;
                int marginTB= 5*Screenheight/100;
                paramsbg.setMargins(marginLR, marginTB, marginLR, marginTB);
//                backGroundImage.setLeftTopRightBottom(marginLR, marginTB, marginLR, marginTB);
                backGroundImage.setLayoutParams(paramsbg);
                final int[] width = {backGroundImage.getWidth()};
                final int[] height = {backGroundImage.getHeight()};
//                final int heigt =backGroundImage.getMeasuredHeight();
//                ViewTreeObserver vto = backGroundImage.getViewTreeObserver();
//                vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//                    int finalHeight, finalWidth;
//                    public boolean onPreDraw() {
//                        backGroundImage.getViewTreeObserver().removeOnPreDrawListener(this);
//                        height[0] = backGroundImage.getMeasuredHeight();
//                        width[0] = backGroundImage.getMeasuredWidth();
//
//
//                        return true;
//                    }
//                });
//
//


//                int width = backGroundImage.getDrawable().getIntrinsicWidth();
//                int   height = backGroundImage.getDrawable().getIntrinsicHeight();
//                showMessage("Error", String.valueOf(height + "" + width));

                params.leftMargin = x * Screenwidth/1000;
                params.topMargin = y * Screenheight /1000;
                params.height = 10 * Screenheight/100;
                params.width = 20 * Screenwidth /100;

//                params.leftMargin = x * width[0]/1000;
//                params.topMargin = y * height[0] /1000;
//                params.height = 10 * width[0]/100;
//                params.width = 20 * height[0] /100;



                mRelativeLayout.addView(tv, params);
                tv.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {
                        showMessage("Error", String.valueOf(tv.getId()));
//                        formDialog();
                    }
                });
                mRelativeLayout.setBackgroundColor(Color.parseColor("#000000"));
            }

        }



//
//        mRelativeLayout.setBackgroundColor(Color.BLACK);
//        addContentView(tv,params);

    }
    public void showMessage(String title, String  message){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder. setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
//    public void formDialog(){
//        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//        View layout = inflater.inflate(R.layout.dialog_layout, (ViewGroup) findViewById(R.id.layout_root));
////layout_root should be the name of the "top-level" layout node in the dialog_layout.xml file.
//        final EditText nameBox = (EditText) layout.findViewById(R.id.name_box);
//        final EditText phoneBox = (EditText) layout.findViewById(R.id.phone_box);
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setView(layout);
//        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//                //save info where you want it
//            }
//        });
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//        AlertDialog dialog = builder.create();
//        builder.show();
//
//    }
}
