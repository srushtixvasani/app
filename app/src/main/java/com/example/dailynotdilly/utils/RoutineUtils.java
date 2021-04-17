package com.example.dailynotdilly.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RoutineUtils {

    public static String formatTime(Date timeSet) {
        SimpleDateFormat simpleTimeFormat = (SimpleDateFormat) SimpleDateFormat.getTimeInstance();
        simpleTimeFormat.applyPattern("HH:mm");

        return simpleTimeFormat.format(timeSet);
    }

    public static String formatMinute(Date minuteSet) {
        SimpleDateFormat simpleTimeFormat = (SimpleDateFormat) SimpleDateFormat.getTimeInstance();
        simpleTimeFormat.applyPattern("mm");

        return simpleTimeFormat.format(minuteSet);
    }

    public static String formatTimerMinute(Date minuteSet) {
        SimpleDateFormat simpleTimeFormat = (SimpleDateFormat) SimpleDateFormat.getTimeInstance();
        simpleTimeFormat.applyPattern("mm:ss");
        simpleTimeFormat.setLenient(false);
        return simpleTimeFormat.format(minuteSet);
    }


}
