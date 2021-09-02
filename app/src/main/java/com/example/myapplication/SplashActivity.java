package com.example.myapplication;

import android.content.Intent;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {
    private static int splash_screen_timeout = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        Animation fadeout = new AlphaAnimation(1,0);
        fadeout.setInterpolator(new AccelerateInterpolator());
        fadeout.setStartOffset(500);
        fadeout.setDuration(1800);
        TextView textView = findViewById(R.id.textView6);
        textView.setAnimation(fadeout);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },splash_screen_timeout);
    }
}