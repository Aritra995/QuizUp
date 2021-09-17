package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class FeedbackActivity extends AppCompatActivity {
    private static final String TAG = "FeedbackActivity";
    ImageView close,send,thumbsUp,thumbsUpGreen,thumbsDown,thumbsDownRed;
    RatingBar ratingBar;
    EditText feedbackMsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_feedback);

        close = findViewById(R.id.closeFeedback);
        send = findViewById(R.id.sendFeedback);
        ratingBar = findViewById(R.id.ratingBar);
        thumbsUp = findViewById(R.id.thumbsUp);
        thumbsUpGreen = findViewById(R.id.thumbsUpGreen);
        thumbsDown = findViewById(R.id.thumbsDown);
        thumbsDownRed = findViewById(R.id.thumbsDownRed);
        feedbackMsg = findViewById(R.id.feedbackTxt);

        int color = Color.parseColor("#228B22");
        int colorUnSelect = Color.parseColor("#66000000");
        thumbsUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thumbsUpGreen.setVisibility(View.VISIBLE);
                thumbsDownRed.setVisibility(View.GONE);
            }
        });
        thumbsDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thumbsDownRed.setVisibility(View.VISIBLE);
                thumbsUpGreen.setVisibility(View.GONE);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FeedbackActivity.this,StudentsPortalActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send to firebase
            }
        });
    }
}