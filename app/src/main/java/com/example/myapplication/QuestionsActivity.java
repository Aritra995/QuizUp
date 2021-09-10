package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.sax.EndElementListener;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.*;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class QuestionsActivity extends AppCompatActivity implements EndTestDialog.EndTestDialogListener, ExitAppDialog.ExitAppListener {
    private static final String TAG = "QuestionsActivity";
    RecyclerView recyclerView;
    QuestionsAdapter adapter;
    ArrayList<Questions> list;
    TextView timer;
    Button endTest;
    CountDownTimer countDownTimer;
    private String category;
    private ProgressBar progressBar3;
    RadioButton option1,option2,option3,option4;
    RadioGroup radioGroup;

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

        progressBar3 = findViewById(R.id.progressBar3);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new QuestionsAdapter(this,list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        Intent intent = this.getIntent();
        category = intent.getStringExtra("category");

        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("questions").child(category);
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
                calculateScore();
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
        calculateScore();
    }
    private void calculateScore(){
        progressBar3.setVisibility(View.VISIBLE);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("questions").child(category);
        reference.addValueEventListener(new ValueEventListener() {
            private int score;
            private int i =0;

            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Questions questionsModal = dataSnapshot.getValue(Questions.class);
                    option1 = recyclerView.findViewById(R.id.option1);
                    option2 = recyclerView.findViewById(R.id.option2);
                    option3 = recyclerView.findViewById(R.id.option3);
                    option4 = recyclerView.findViewById(R.id.option4);
//                    RadioButton checked1 = recyclerView.findViewHolderForItemId(R.id.option1);
//                    option2 = recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.option2);
//                    option3 = recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.option3);
//                    option4 = recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.option4);
                    //findViewHolderForAdapterPosition
                    if( option1.isChecked() ){
                        if( questionsModal.getCorrect().trim().toLowerCase().equals(option1.getText().toString().trim().toLowerCase()) ){
                            this.score += 5;
                        }
                        else{
                            this.score -= 1;
                        }
                    }
                    else if(option2.isChecked()){
                        if( questionsModal.getCorrect().trim().toLowerCase().equals(option2.getText().toString().trim().toLowerCase()) ){
                            this.score += 5;
                        }
                        else{
                            this.score -= 1;
                        }
                    }
                    else if(option3.isChecked()){
                        if( questionsModal.getCorrect().trim().toLowerCase().equals(option3.getText().toString().trim().toLowerCase()) ){
                            Log.d(TAG,"opt correct: 3");
                            this.score += 5;
                        }
                        else{
                            Log.d(TAG,"opt: 3");
                            this.score -= 1;
                        }
                    }
                    else if(option4.isChecked()){
                        if( questionsModal.getCorrect().trim().toLowerCase().equals(option4.getText().toString().trim().toLowerCase()) ){
                            this.score += 5;
                        }
                        else{
                            this.score -= 1;
                        }
                    }
                    else{
                        this.score -= 1;
                    }
                    i++;
                }
                //adapter.notifyDataSetChanged();

                progressBar3.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(QuestionsActivity.this,StudentsPortalActivity.class);
                Log.d(TAG,"score: "+ score);
                intent.putExtra("score", score);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        ExitAppDialog exitAppDialog = new ExitAppDialog();
        exitAppDialog.show(getSupportFragmentManager(),"end quiz");
    }

    @Override
    public void onExitNowYesClicked() {
        countDownTimer.cancel();
        calculateScore();
    }

    @Override
    public String setTitle() {
        return "End Quiz?";
    }

    @Override
    public String setMessage() {
        return "Are you sure you want to end quiz?";
    }
}