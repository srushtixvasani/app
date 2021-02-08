package com.example.dailynotdilly;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends Activity {

    // http://www.sherif.mobi/2012/09/how-to-create-splash-activity-best.html
   private static final long SPLASH_TIME = 4000; //4 seconds
   Handler handler;
   Runnable jumpRunnable;

   // allows user to jump to Main Activity
    private void jump() {
        if(isFinishing())
            return;
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);

        try {
            VideoView videoView = new VideoView(this);
            setContentView(videoView);

            Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.splash_video);

            videoView.setVideoURI(video);
            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    jump();
                }
            });

            videoView.start();
        } catch (Exception ex){
            jump();
        }

        jumpRunnable = new Runnable() {
            @Override
            public void run() {
                jump();
            }
        };

        handler = new Handler();
        handler.postDelayed(jumpRunnable, SPLASH_TIME);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        jump();
        return true;
    }
}
