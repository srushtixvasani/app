package com.example.dailynotdilly.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.dailynotdilly.models.Priority;
import com.example.dailynotdilly.models.ToDoTask;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String formatDate(Date date) {
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();

        // set date pattern
        simpleDateFormat.applyPattern("EEE, MMM d ");
        return simpleDateFormat.format(date);

    }

    public static String formatTime(Date timeSet) {
        SimpleDateFormat simpleTimeFormat = (SimpleDateFormat) SimpleDateFormat.getTimeInstance();
        simpleTimeFormat.applyPattern("HH:mm");

        return simpleTimeFormat.format(timeSet);
    }

    public static void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static int priorityColor(ToDoTask toDoTask) {
        int color;

        if (toDoTask.getPriority() == Priority.HIGH) {
            color = Color.argb(200, 201, 21, 23);
        } else if (toDoTask.getPriority() == Priority.MEDIUM) {
            color = Color.argb(200, 255 , 179 , 0);
        } else{
            color = Color.argb(200, 51, 181, 229 );
        }

        return color;
    }
}
