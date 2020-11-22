package com.example.restaurantreservation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MapPage extends AppCompatActivity {
    DatabaseHelper myDb;
    Dialog dialog;

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
        final String[] convertedDate = new String[1];
        final String[] convertedStartTime = new String[1];
        final String[] convertedEndTime = new String[1];
        setContentView(R.layout.activity_map_page);
        myDb = new DatabaseHelper(this);
        myDb.getAllData();
        Cursor result = myDb.getAllData();
        Bundle extras =getIntent().getExtras();
        final String Phone =extras.getString("phone");
        final String Fullname =extras.getString("fullname");
        String Date =extras.getString("date");
        String StartHour =extras.getString("startH");
        String EndHour =extras.getString("endH");
        try {
            convertedDate[0] =formatDate(Date);
            convertedStartTime[0] =formatDateTime(StartHour, convertedDate[0]);
            convertedEndTime[0] =formatDateTime(EndHour, convertedDate[0]);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        boolean isTableAlreadyBooked;
        if (result.getCount() == 0){
//            showMessage("Error", "Keine Tupel in der Tabelle");
                return;
        }else {
            while(result.moveToNext()){

                x = Integer.parseInt(result.getString(1));
                y = Integer.parseInt(result.getString(2));
                id =Integer.parseInt(result.getString(0));
                 isTableAlreadyBooked =isTableBooked(convertedDate[0],convertedStartTime[0], convertedEndTime[0],id);


                RelativeLayout mRelativeLayout = (RelativeLayout)findViewById(R.id.wrapper);
                final ImageView tv = new ImageView(this);
                tv.setId(id);

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                String mDrawableName;
                if(isTableAlreadyBooked ==false){
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

                final ImageView backGroundImage=(ImageView)findViewById(R.id.imageView2);
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

                tv.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {
                        Integer resource = (Integer)tv.getTag();

                        if(finalIsTableAlreadyBooked ==false && resource== R.drawable.freetable){
                          bookingDialog("Are you sure?", Fullname, Phone,  convertedDate[0], convertedStartTime[0], convertedEndTime[0], tv.getId(), tv);

                        }
                        else{
                            bookedTableDialog("Sorry already booked");
                        }

                    }
                });

            }

        }

    }
    public boolean isTableBooked(String date, String startHour, String endHour ,int id){
        Cursor bookedTable=myDb.getBookedTables(date,startHour,endHour,id);
        if (bookedTable.getCount() == 0) {
            return false;
        }
        else return true;
    }
//    public void showMessage(String title, String  message){
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder. setCancelable(true);
//        builder.setTitle(title);
//        builder.setMessage(message);
//        builder.show();
//    }

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
    public void bookingDialog(String message, final String name, final String phone, final String date, final String startHour, final String endHour, final Integer tableid, final ImageView tv){
        dialog= new Dialog(MapPage.this);
        dialog.setContentView(R.layout.custom_dialog_booking);
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
        Button confirm =dialog. findViewById(R.id.btn_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean ok=myDb.insertBooking(name, phone, date, startHour, endHour, tableid);
                Toast.makeText(MapPage.this, String.valueOf(ok), Toast.LENGTH_SHORT).show(); // for testing
                int resID = getResources().getIdentifier("bookedtable" , "drawable", getPackageName());
                tv.setImageResource(resID);
                dialog.dismiss();
                tv.setTag(R.drawable.bookedtable);



            }
        });
        dialog.show();

    }
    public void bookedTableDialog(String message){
        dialog= new Dialog(MapPage.this);
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
