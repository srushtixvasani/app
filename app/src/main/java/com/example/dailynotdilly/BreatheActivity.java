package com.example.dailynotdilly;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import com.github.florent37.viewanimator.ViewAnimator;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BreatheActivity extends AppCompatActivity {

    private ImageView breatheImage;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.breathe_activity);

        // set floating action button to open home screen
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BreatheActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        breatheImage = findViewById(R.id.breatheImage);

        ViewAnimator
                .animate(breatheImage)
                .translationY(-1000, 0)
                .alpha(0,1)
                .dp().translationX(-20, 0)
                .decelerate()
                .duration(2000)
                .thenAnimate(breatheImage)
                .scale(1f, 0.5f, 1f)
                .rotation(360f)
                .repeatCount(10)
                .accelerate()
                .duration(2000)
                .start();

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
