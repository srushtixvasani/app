package com.example.dailynotdilly;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class EveningActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.evening_routine);

        // Opens up Home Activity
        ImageButton homeActivity = findViewById(R.id.homeActivity);
        homeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EveningActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Opens up morning Activity
        ImageButton morningActivity = findViewById(R.id.morningRoutine);
        morningActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EveningActivity.this, MorningActivity.class);
                startActivity(intent);
            }
        });

        // Opens up Afternoon Activity
        ImageButton afternoonActivity = findViewById(R.id.afternoonRoutine);
        afternoonActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EveningActivity.this, AfternoonActivity.class);
                startActivity(intent);
            }
        });

    }

}
