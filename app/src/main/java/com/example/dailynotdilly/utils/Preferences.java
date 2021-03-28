package com.example.dailynotdilly.utils;

import android.app.Activity;
import android.content.SharedPreferences;

import java.util.Calendar;

public class Preferences {

    private SharedPreferences preferences;

    // create shared preferences which will be saved in the device
    public Preferences(Activity activity){
        this.preferences = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    public void setBreathsTaken(int numOfBreaths){
        // saves the number of breaths taken
        preferences.edit().putInt("numOfBreaths" , numOfBreaths).apply();
    }

    public void setSessions(int session){
        // saves the number of sessions taken
        preferences.edit().putInt("sessions" , session).apply();
    }

    public void setDate(long milliSecs){
        preferences.edit().putLong("seconds", milliSecs).apply();
    }

    public int getBreathsTaken(){
        return preferences.getInt("numOfBreaths", 0);
    }

    public int getSessions(){
        return preferences.getInt("sessions", 0);
    }

    public String getDate(){
        long dateInMilli = preferences.getLong("seconds", 0);
        String amPm; // if the date is in am or pm

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateInMilli);

        int x = calendar.get(Calendar.AM_PM);
        if (x == Calendar.AM)
            amPm = "AM";
        else
            amPm = "PM";

        String time = "Last session at " + calendar.get(Calendar.HOUR_OF_DAY) + ":" +
                calendar.get(Calendar.MINUTE) + " " + amPm + " on " + calendar.get(Calendar.DATE) +
                "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.YEAR);
        return time;
    }




}
