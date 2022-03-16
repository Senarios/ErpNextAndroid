package com.example.erpnext.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.erpnext.R;
import com.example.erpnext.app.AppSession;
import com.example.erpnext.utils.Constants;

public class SplashActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    private Button singUp, logIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView(); //set status background black
        decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_splash);

        initViews();
        animation();

        Thread thr = new Thread() {
            public void run() {
                try {
                    sleep(1000);

                } catch (Exception exception) {
                    exception.printStackTrace();
                } finally {
                    if (AppSession.getBoolean(Constants.IS_LOGGED_IN)) {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    } else {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        finish();
//
                    }
                }
            }
        };
        thr.start();

    }

    private void initViews() {
//        logo = findViewById(R.id.logo);
//        name = findViewById(R.id.splashLogoText);
//        viewLine = findViewById(R.id.viewLine);
//        superAdmin = findViewById(R.id.superAdmin);

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setDuration(3000);

        AnimationSet animation = new AnimationSet(false); //change to false
        animation.addAnimation(fadeIn);
//        logo.setAnimation(animation);
//        name.setAnimation(animation);
//        viewLine.setAnimation(animation);
//        superAdmin.setAnimation(animation);
    }

    private void animation() {
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setDuration(3000);

        AnimationSet animation = new AnimationSet(false); //change to false
        animation.addAnimation(fadeIn);
//        logo.setAnimation(animation);
//        name.setAnimation(animation);
//        viewLine.setAnimation(animation);

    }
}