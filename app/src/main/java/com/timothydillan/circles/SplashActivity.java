package com.timothydillan.circles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.android.gms.fitness.Fitness;
import com.timothydillan.circles.Utils.CircleUtil;
import com.timothydillan.circles.Utils.FirebaseUtil;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        FirebaseUtil.initializeFirebaseDbAuthStorage();
        FirebaseUtil.initializeCurrentFirebaseUser();
        // postDelayed(Runnable r, Object token, long delayMillis)
        // Causes the Runnable r to be added to the message queue, to be run after the specified amount of time elapses.
        // In here, we override the run method that belongs to the Runnable object and instead load the Main Activity
        // after 3 seconds.
        final int SPLASH_SCREEN_DURATION = 3000;

        TextView appTitleTextView = findViewById(R.id.appNameLabel);
        Animation slideInAnimation = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        slideInAnimation.setDuration(2000);
        appTitleTextView.startAnimation(slideInAnimation);

        new Handler().postDelayed(() -> {
            Intent activity = new Intent();
            if (FirebaseUtil.getCurrentUser() != null) {
                activity.setClass(this, MainActivity.class);
            } else {
                activity.setClass(this, SignUpActivity.class);
            }
            startActivity(activity);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }, SPLASH_SCREEN_DURATION);

    }
}