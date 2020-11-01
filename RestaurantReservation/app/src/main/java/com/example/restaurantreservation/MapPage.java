package com.example.restaurantreservation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MapPage extends AppCompatActivity {
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int x=0;
        int y=0;
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
//            showMessage("Error", String.valueOf(x));

                RelativeLayout mRelativeLayout = (RelativeLayout)findViewById(R.id.wrapper);
                ImageView tv = new ImageView(this);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                String mDrawableName = "table";
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
//                final int[] width = new int[1];
//                final int[] height = {backGroundImage.getHeight()};
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
//                backGroundImage.post(new Runnable() {
//                    @Override
//                    public void run() {
//                       width[0] = backGroundImage.getMeasuredWidth();
//                       height[0] = backGroundImage.getMeasuredHeight();
//                        //showMessage("Error", String.valueOf(height[0]));
//                    }
//                });
                int width = backGroundImage.getDrawable().getIntrinsicWidth();
                int   height = backGroundImage.getDrawable().getIntrinsicHeight();
//                showMessage("Error", String.valueOf(height + "" + width));

                params.leftMargin = x * 1080/1000;
                params.topMargin = y * 1920 /1000;
                params.height = 10 * 1920/100;
                params.width = 20 * 1080 /100;


                mRelativeLayout.addView(tv, params);
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
}
