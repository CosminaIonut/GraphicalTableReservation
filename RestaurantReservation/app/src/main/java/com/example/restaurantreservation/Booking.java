package com.example.restaurantreservation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Objects;

public class Booking extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    boolean end= false;
    boolean isToday=false;
    ValidationBookindDetails validate =new ValidationBookindDetails();
    boolean pastTime=true;
    boolean endTbefrorestartT=false;
    int startHo=0, endHo=0, startM=0, endM=0;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        final TextView datePick = (TextView) findViewById(R.id.date_pick);
        final TextView startH =(TextView) findViewById(R.id.startHour);
        final TextView endH =(TextView) findViewById(R.id.endHour);
        Button buttonBooking = findViewById(R.id.booktableButton);
        final EditText phone= findViewById(R.id.phone);
        final EditText fullName= findViewById(R.id.fullName);
        Button cancelButton = findViewById(R.id.candelbooking);


        datePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker= new DatepickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");

            }
        });
        startH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(),"time Picker");
                end =false;
            }
        });
        endH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(),"time Picker");
                end =true;
            }
        });

        buttonBooking.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(pastTime && endTbefrorestartT){

                    Intent tableActivity = new Intent(getApplicationContext(), MapPage.class);
                    startActivity(tableActivity);
                }
                else{
                    if(endM ==0 || endHo==0 || startHo==0 || startM ==0||  TextUtils.isEmpty(phone.getText()) || TextUtils.isEmpty(fullName.getText())){
                        String message= "Fields cannot be empty";
                        errorDialog(message);
                    }
                    else if(!pastTime){
                        String message= "The start hour musst be after the current hour";
                        errorDialog(message);
                    }
                    else {
                        String message= "The end time musst be after the start time";
                        errorDialog(message);
                    }


                }

            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent restaurantHome = new Intent(getApplicationContext(), RestaurantHomePage.class);
                startActivity(restaurantHome);
            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            Calendar c =Calendar.getInstance();
            c.set(Calendar.YEAR,year );
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, day);
            String currentDateString = DateFormat.getDateInstance().format(c.getTime());
            TextView textView = (TextView) findViewById(R.id.date_pick);
            textView.setText(currentDateString);
            isToday=validate.isToday(year,month,day);
            pastTime=true;
            if(isToday){
            pastTime=validate.isTimeValid(startHo, endHo);
        }

        endTbefrorestartT=validate.isStartAfterEndValid(startHo,startM,endHo,endM);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {

       String min="";
        if(minute<9){
           min=0+""+minute;
        }
        else{
            min=""+minute;
        }
        if(!end){
            TextView startHour=(TextView)findViewById(R.id.startHour);
            startHour.setText(hour+":"+ min);
            startHo=hour;
            startM=minute;
        }
        else{
            TextView endHour=(TextView)findViewById(R.id.endHour);
            endHour.setText(hour+":"+ min);
            endHo=hour;
            endM=minute;

        }
        if(isToday){
            pastTime=validate.isTimeValid(startHo, startM);
        }

        endTbefrorestartT=validate.isStartAfterEndValid(startHo,startM,endHo,endM);


    }
    public void errorDialog(String message){
        dialog= new Dialog(Booking.this);
        dialog.setContentView(R.layout.custom_dialog);
        TextView textDialog=dialog.findViewById(R.id.text_dialog);
        textDialog.setText(message);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_background));
//                    Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().getAttributes().windowAnimations=R.style.animation;
        dialog.setCancelable(false);
        Button cancel =dialog.findViewById(R.id.btn_dialog);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
}
