package com.example.dailynotdilly;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BreatheActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private ImageView breatheImage;
    private AppCompatTextView minute;
    private AppCompatTextView numOfBreathTaken;
    private AppCompatTextView minsToday;
    private MaterialButton startButton;
    private MaterialButton stopButton;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.breathe_activity);

        // set floating action button to open home screen
        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BreatheActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Statistic for the user
        minute = findViewById(R.id.minute);
        numOfBreathTaken = findViewById(R.id.numOfBreathTaken);
        minsToday = findViewById(R.id.minsToday);




        // for the animation to go smaller when user inhales, larger when user exhales when start button is pressed
        breatheImage = findViewById(R.id.breatheImage);

        startButton = findViewById(R.id.breatheButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimation();
            }

        });

        // to stop animation
        stopButton = findViewById(R.id.stopBreathButton);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAnimation();
            }
        });


       }

       private void startAnimation(){
           ViewAnimator
                   .animate(breatheImage)
                   .scale(1f, 0.75f, 0.5f, 0.75f, 1f)
                   .decelerate()
                   .duration(7000)
                   .repeatCount(ViewAnimator.INFINITE)
                   .start();
       }

       private void stopAnimation(){
            ViewAnimator.animate(breatheImage)
                    .start().cancel();
       }


//    private View.OnClickListener startViewAnimation() {
//        ImageView breatheImage = findViewById(R.id.breatheImage);
//        ViewAnimator viewAnimator = new ViewAnimator();
//
//        viewAnimator.animate(breatheImage)
//                .rotation(360)
//                .start();
////                .scale(0.2f,1.5f,0.02f)
////                .rotation(360f)
////                .repeatCount(10)
////                .accelerate()
////                .duration(20000)
////                .onStop(() -> {
////                    breatheImage.setScaleX(1.0f);
////                    breatheImage.setScaleY(1.0f);
////                })
////                .onStart();
//    }
}
