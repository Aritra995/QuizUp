package com.example.myapplication;

import android.view.View;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ExitAppDialog.ExitAppListener {
    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onBackPressed() {
        ExitAppDialog exitAppDialog = new ExitAppDialog();
        exitAppDialog.show(getSupportFragmentManager(),"exit app");
    }
    @Override
    public void onClick(View view) {
    }

    @Override
    public void onExitNowYesClicked() {
        super.onBackPressed();
    }

    @Override
    public String setTitle() {
        return "Exit QuizUp?";
    }

    @Override
    public String setMessage() {
        return "Are you sure you want to exit?";
    }
}