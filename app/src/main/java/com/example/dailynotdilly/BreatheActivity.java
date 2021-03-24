package com.example.dailynotdilly;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.dailynotdilly.utils.Preferences;
import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.MessageFormat;

public class BreatheActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private ImageView breatheImage;
    private AppCompatTextView lastSession;
    private AppCompatTextView numOfBreathTaken;
    private AppCompatTextView session;
    private MaterialButton startButton;
    private Preferences preferences;
    private ImageButton infoButton;


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

        // information for the user on how to use the Breathe feature
        infoButton = findViewById(R.id.info_button);
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfoWindow(v);
            }
        });

        // Statistic for the user
        preferences = new Preferences(this);
        lastSession = findViewById(R.id.lastSession);
        numOfBreathTaken = findViewById(R.id.numOfBreathTaken);
        session = findViewById(R.id.minsToday);

        session.setText(MessageFormat.format("{0} minutes total", preferences.getSessions()));
        numOfBreathTaken.setText(MessageFormat.format("{0} breaths taken", preferences.getBreathsTaken()));
        lastSession.setText(preferences.getDate());

        // for the animation to go smaller when user inhales, larger when user exhales when start button is pressed
        breatheImage = findViewById(R.id.breatheImage);

        // to start animation
        startButton = findViewById(R.id.breatheButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimation();
            }
        });
       }

       private void startAnimation(){
           ViewAnimator
                   .animate(breatheImage)
                   .scale(1f, 0.75f, 0.5f, 0.75f, 1f)
                   .decelerate()
                   .duration(7000)
                   .repeatCount(85) //should be 85 for the session to be 10 minutes
                   .onStop(new AnimationListener.Stop() {
                       @Override
                       public void onStop() {
                           breatheImage.setScaleX(1f);
                           breatheImage.setScaleX(1f);

                           preferences.setDate(System.currentTimeMillis());
                           preferences.setSessions(preferences.getSessions() + 10);
                           preferences.setBreathsTaken(preferences.getBreathsTaken() + 86);

                           // when duration is completed, the activity should be refreshed
                           new CountDownTimer(2500, 1000){

                               @Override
                               public void onTick(long millisUntilFinished) {
                                   // code to show ticking, but it is not needed in this case
                               }

                               @Override
                               public void onFinish() {
                                    startActivity(new Intent(getApplicationContext(), BreatheActivity.class));
                                    finish();
                               }
                           }.start();
                       }
                   })
                   .start();
       }

    // info window using PopupWindow to be used with the info_button.
    public void showInfoWindow(View view) {

        // inflate the layout of the info window
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View infoWindow = inflater.inflate(R.layout.info_window, null);

        // create the info window
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        final PopupWindow infoPopup = new PopupWindow(infoWindow, width, height, true);

        // shows the info window
        infoPopup.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismisses the info window when clicked
        infoWindow.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                infoPopup.dismiss();
                return true;
            }
        });
    }

}
