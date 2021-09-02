package com.example.myapplication;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;

public class EmailValidationActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button verifyEmailButton;
    private static final String TAG = "EmailValidationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_validation);
        mAuth = FirebaseAuth.getInstance();
        verifyEmailButton = findViewById(R.id.verifyEmailButton);
        verifyEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailValidation();
            }
        });
    }
    private void emailValidation(){
        FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mAuth.signOut();
                            Toast.makeText(EmailValidationActivity.this,"Verification email sent.Check Inbox",
                                    Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(EmailValidationActivity.this,MainActivity.class);
                            startActivity(intent);
                            Log.d(TAG, "Email sent.");
                        }else{
                            Toast.makeText(EmailValidationActivity.this,"Try again",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}