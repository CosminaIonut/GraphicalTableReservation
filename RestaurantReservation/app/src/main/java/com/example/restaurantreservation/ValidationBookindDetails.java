package com.example.restaurantreservation;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ValidationBookindDetails {

    public boolean isToday(int year, int month, int day){
        Calendar c= Calendar.getInstance();
        int currentYear= c.get(Calendar.YEAR);
        int currentMonth = c.get(Calendar.MONTH);
        int currentDay = c.get(Calendar.DAY_OF_MONTH);
        if(currentDay == day && currentMonth==month && currentYear==year){
            return true;
        }
        return false;
    }
    public boolean isTimeValid( int hour, int minute){
        Calendar c= Calendar.getInstance();
        int currentHour =c.get(Calendar.HOUR_OF_DAY);
        int currentMinute = c.get(Calendar.MINUTE);
        System.out.println(currentMinute);
        System.out.println(minute);
        System.out.println(hour);

        if(hour < currentHour){
            return false;
        }
        if(hour ==currentHour && minute < currentMinute)
        {
            return false;
        }
        return true;

    }
    public boolean isStartAfterEndValid(int startHour, int startMinute, int endHour, int endMinute){

        if(startHour> endHour ){
            return false;
        }
        if(startHour==endHour && startMinute>endMinute){
            return false;
        }
        return true;
    }

}
