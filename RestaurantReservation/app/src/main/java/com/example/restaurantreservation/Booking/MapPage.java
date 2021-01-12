package com.example.restaurantreservation.Booking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.restaurantreservation.DatabaseHelper;
import com.example.restaurantreservation.HomePage.MainActivity;
import com.example.restaurantreservation.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MapPage extends AppCompatActivity {
    DatabaseHelper myDb;
    Dialog dialog;
    ImageView restaurantmap;
    ImageView homeBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_page);

        homeBtn = findViewById(R.id.homeBtn);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeActivity = new Intent(getApplicationContext(), MainActivity.class);
                finish();
                startActivity(homeActivity);
                Booking.book.finish();
                MainActivity.main.finish();
            }
        });

        Bundle extras =getIntent().getExtras();
        int idR = Integer.parseInt(extras.getString("RestaurantID"));
        String mdrawable="map"+idR;

        // Add map file to ImageView.
        final int res = getResources().getIdentifier(mdrawable , "drawable", getPackageName());
        restaurantmap = findViewById(R.id.imageView2);
        restaurantmap.setImageResource(res);

        int x=0;
        int y=0;
        int id=0;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int Screenheight = displayMetrics.heightPixels;
        int Screenwidth = displayMetrics.widthPixels;


        myDb = new DatabaseHelper(this);
        myDb.getAllData(idR);
        Cursor result = myDb.getAllData(idR);

        final String[] convertedDate = new String[1];
        final String[] convertedStartTime = new String[1];
        final String[] convertedEndTime = new String[1];

        final String Phone = extras.getString("phone");
        final String Fullname = extras.getString("fullname");
        String Date = extras.getString("date");
        String StartHour = extras.getString("startH");
        String EndHour = extras.getString("endH");

        try {
            convertedDate[0] = formatDate(Date);
            convertedStartTime[0] = formatDateTime(StartHour, convertedDate[0]);
            convertedEndTime[0] = formatDateTime(EndHour, convertedDate[0]);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Change display of the table button depending on booking availability.
        boolean isTableAlreadyBooked;
        if (result.getCount() == 0){
                return;
        }else {
            while(result.moveToNext()){

                x = Integer.parseInt(result.getString(1));
                y = Integer.parseInt(result.getString(2));
                id =Integer.parseInt(result.getString(0));
                isTableAlreadyBooked = isTableBooked(convertedDate[0],convertedStartTime[0], convertedEndTime[0],id);

                RelativeLayout mRelativeLayout = (RelativeLayout)findViewById(R.id.wrapper);
                final ImageView tv = new ImageView(this);
                tv.setId(id);

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                String mDrawableName;
                if(!isTableAlreadyBooked){
                    mDrawableName = "freetable";
                    tv.setTag(R.drawable.freetable);
                }
                else{
                    mDrawableName = "bookedtable";
                    tv.setTag(R.drawable.bookedtable);
                }

                final int resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
                tv.setImageResource(resID);
                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);

                final ImageView backGroundImage = (ImageView)findViewById(R.id.imageView2);
                RelativeLayout.LayoutParams paramsbg = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );

                int marginLR= 5*Screenwidth/100;
                int marginTB= 5*Screenheight/100;
                paramsbg.setMargins(marginLR, marginTB, marginLR, marginTB);
                backGroundImage.setLayoutParams(paramsbg);

                params.leftMargin = x * Screenwidth/1000;
                params.topMargin = y * Screenheight /1000;
                params.height = 10 * Screenheight/100;
                params.width = 20 * Screenwidth /100;

                mRelativeLayout.addView(tv, params);
                final boolean finalIsTableAlreadyBooked = isTableAlreadyBooked;

                // Table button response if booking attempt is made on already booked table.
                tv.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {
                        Integer resource = (Integer)tv.getTag();

                        if(finalIsTableAlreadyBooked ==false && resource== R.drawable.freetable){
                          bookingDialog("Final pick?", Fullname, Phone,  convertedDate[0], convertedStartTime[0], convertedEndTime[0], tv.getId(), tv);
                        }
                        else{
                            bookedTableDialog("Sorry already booked");
                        }
                    }
                });
            }
        }
    }

    /*
    * Call on db function to check if table is already booked within hours given as params.
    * */
    public boolean isTableBooked(String date, String startHour, String endHour ,int id){
        Cursor bookedTable=myDb.getBookedTables(date,startHour,endHour,id);
        if (bookedTable.getCount() == 0) {
            return false;
        }
        else return true;
    }

    /////   DIALOG FUNCTIONS    ////////////////////////////////////////////////////////////////////

    /*
    * Pop-up Dialog to confirm/cancel booking.
    * Confirm adds booking data in the db.
    * */
    public void bookingDialog(String message, final String name, final String phone, final String date, final String startHour, final String endHour, final Integer tableid, final ImageView tv){
        dialog = new Dialog(MapPage.this);
        dialog.setContentView(R.layout.custom_dialog_booking);
        TextView textDialog=dialog.findViewById(R.id.text_dialog);
        textDialog.setText(message);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_background));
        dialog.getWindow().getAttributes().windowAnimations=R.style.animation;
        dialog.setCancelable(false);

        Button cancel = dialog.findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Button confirm = dialog. findViewById(R.id.btn_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean ok = myDb.insertBooking(name, phone, date, startHour, endHour, tableid);
                int resID = getResources().getIdentifier("bookedtable" , "drawable", getPackageName());
                tv.setImageResource(resID);
                dialog.dismiss();
                tv.setTag(R.drawable.bookedtable);
            }
        });

        dialog.show();
    }

    /*
    * Pop-up Dialog if an already booked table is clicked.
    */
    public void bookedTableDialog(String message){
        dialog = new Dialog(MapPage.this);
        dialog.setContentView(R.layout.booked_table_dialog);
        TextView textDialog=dialog.findViewById(R.id.text_dialog);
        textDialog.setText(message);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_background));
        dialog.getWindow().getAttributes().windowAnimations=R.style.animation;
        dialog.setCancelable(false);

        Button cancel =dialog.findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    /////   DATE/DATE-TIME FORMAT FUNCTIONS ////////////////////////////////////////////////////////

    public String formatDate(String date) throws ParseException {
        DateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        Date convertedDate = sdf.parse(date);

        String newDate= new SimpleDateFormat("yyyy-MM-dd").format(convertedDate);
        System.out.println("yyyy-MM-dd formatted date : " + newDate);
        return newDate;
    }

    public String formatDateTime(String time, String date) throws ParseException {
         String dateTime= date+" "+time+":00";
         return dateTime;
    }
}
