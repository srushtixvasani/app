package com.example.dailynotdilly;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class AfternoonActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.afternoon_activity);

        // Opens up Home Activity
        ImageButton homeButton = findViewById(R.id.home);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AfternoonActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Opens up morning Activity
        ImageButton morningButton = findViewById(R.id.morning);
        morningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AfternoonActivity.this, MorningActivity.class);
                startActivity(intent);
            }
        });

        // Opens up Evening Activity
        ImageButton eveningButton = findViewById(R.id.evening);
        eveningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AfternoonActivity.this, EveningActivity.class);
                startActivity(intent);
            }
        });

    }

}