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
        simpleTimeFormat.applyPattern("MM:ss");

        return simpleTimeFormat.format(minuteSet);
    }
}
