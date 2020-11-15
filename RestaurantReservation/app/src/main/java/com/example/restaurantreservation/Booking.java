package com.example.restaurantreservation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;

public class Booking extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    boolean end= false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
//        ImageButton button =(ImageButton) findViewById(R.id.datePicker);
        TextView datePick = (TextView) findViewById(R.id.date_pick);
        TextView startH =(TextView) findViewById(R.id.startHour);
        TextView endH =(TextView) findViewById(R.id.endHour);
//        ImageButton startTime =(ImageButton) findViewById(R.id.startHour);
//        ImageButton endTime =(ImageButton) findViewById(R.id.endHour);
//        final boolean[] end = {false};
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
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        if(!end){
            TextView startHour=(TextView)findViewById(R.id.startHour);
            startHour.setText(hour+":"+ minute);
        }
        else{
            TextView endHour=(TextView)findViewById(R.id.endHour);
            endHour.setText(hour+":"+ minute);
        }


    }
}
