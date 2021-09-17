package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class FeedbackActivity extends AppCompatActivity {
    private static final String TAG = "FeedbackActivity";
    private FirebaseAuth mAuth;
    ImageView close,send,thumbsUp,thumbsUpGreen,thumbsDown,thumbsDownRed;
    RatingBar ratingBar;
    EditText feedbackMsg;
    String rating = "0.0";
    String feedbackTxt ="NoFeedback";
    String recommend = "NoRecommend";
    Random random = new Random();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.rating = rating;
        this.feedbackTxt = feedbackTxt;
        this.recommend = recommend;
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_feedback);

        mAuth = FirebaseAuth.getInstance();

        close = findViewById(R.id.closeFeedback);
        send = findViewById(R.id.sendFeedback);
        ratingBar = findViewById(R.id.ratingBar);
        thumbsUp = findViewById(R.id.thumbsUp);
        thumbsUpGreen = findViewById(R.id.thumbsUpGreen);
        thumbsDown = findViewById(R.id.thumbsDown);
        thumbsDownRed = findViewById(R.id.thumbsDownRed);
        feedbackMsg = findViewById(R.id.feedbackTxt);

        ratingBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"Rating: "+ratingBar.getRating());
            }
        });
        thumbsUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thumbsUpGreen.setVisibility(View.VISIBLE);
                thumbsDownRed.setVisibility(View.GONE);
                recommend = "YES";
            }
        });
        thumbsDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thumbsDownRed.setVisibility(View.VISIBLE);
                thumbsUpGreen.setVisibility(View.GONE);
                recommend = "NO";
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
                String txtMsg = feedbackMsg.getText().toString();
                if( txtMsg.length() < 1 ){
                    txtMsg = feedbackTxt;
                }
                String ratingTxt = String.valueOf(ratingBar.getRating());
                int id = Math.abs(random.nextInt());
                String setId = ""+id;
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ratings").child(setId);
                Feedback feedback = new Feedback(ratingTxt,txtMsg,recommend,mAuth.getCurrentUser().getUid());
                reference.setValue(feedback);
                Toast.makeText(FeedbackActivity.this,"Thank You For Your Feedback",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FeedbackActivity.this,StudentsPortalActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}