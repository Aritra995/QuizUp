package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.sax.EndElementListener;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.*;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class QuestionsActivity extends AppCompatActivity implements EndTestDialog.EndTestDialogListener {
    RecyclerView recyclerView;
    QuestionsAdapter adapter;
    ArrayList<Questions> list;
    RadioButton option1,option2,option3,option4;
    TextView timer;
    Button endTest;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // updating status bar color for api 21 and above
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.app_bar_color));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        Toolbar toolbar = findViewById(R.id.toolBarQuiz);
        setSupportActionBar(toolbar);



        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new QuestionsAdapter(this,list);
        recyclerView.setAdapter(adapter);


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("questions").child("General Knowledge");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Questions questionsModal = dataSnapshot.getValue(Questions.class);
                    list.add(questionsModal);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        endTest = findViewById(R.id.endTestButton);
        endTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
        timer = findViewById(R.id.timer);
        countDownTimer = new CountDownTimer(120*1000,1000){

            @Override
            public void onTick(long l) {
                int minutes = (int) l/60000;
                int seconds =(int) l % 60000 /1000;
                String timerTxt="";
                if(minutes < 10){
                    timerTxt += "0";
                }
                timerTxt += minutes;
                timerTxt += ":";
                if(seconds < 10){
                    timerTxt += "0";
                }
                timerTxt += seconds;

                timer.setText(timerTxt);
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(QuestionsActivity.this,StudentsPortalActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        };
        countDownTimer.start();


    }
    public void openDialog(){
        EndTestDialog endTestDialog = new EndTestDialog();
        endTestDialog.show(getSupportFragmentManager(),"endTest dialog");
    }

    @Override
    public void onEndNowClicked() {
        countDownTimer.cancel();
        Intent intent = new Intent(QuestionsActivity.this,StudentsPortalActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}