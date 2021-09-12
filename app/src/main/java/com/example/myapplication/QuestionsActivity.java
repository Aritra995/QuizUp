package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.sax.EndElementListener;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
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
    ArrayList<String> answersList;
    ArrayList<String> selected;
    TextView timer;
    Button endTest;
    CountDownTimer countDownTimer;
    private String category;
    private ProgressBar progressBar3;
    RadioButton option1,option2,option3,option4;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // updating status bar color for api 21 and above
        this.score = score;
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
        answersList = new ArrayList<>();
        selected = new ArrayList<>();
        adapter = new QuestionsAdapter(this,list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        adapter.setonItemClickListener(new QuestionsAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d(TAG,"pos: "+position);
            }
            @Override
            public void onA1Click(int position) {
                option1 = recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.option1);
                selected.add(position,option1.getText().toString());
                Log.d(TAG,"button1 pos: "+position);
                Log.d(TAG,"value: "+option1.getText());
            }
            @Override
            public void onA2Click(int position) {
                option2 = recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.option2);
                selected.add(position,option2.getText().toString());
                Log.d(TAG,"button2 pos: "+position);
                Log.d(TAG,"value: "+option2.getText());
            }
            @Override
            public void onA3Click(int position) {
                option3 = recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.option3);
                selected.add(position,option3.getText().toString());
                Log.d(TAG,"button pos3: "+position);
                Log.d(TAG,"value: "+option3.getText());
            }
            @Override
            public void onA4Click(int position) {
                option4 = recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.option4);
                selected.add(position,option4.getText().toString());
                Log.d(TAG,"button pos4: "+position);
                Log.d(TAG,"value: "+option4.getText());
            }
        });

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
                    answersList.add(questionsModal.getCorrect());
                    selected.add("quizup");
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
        countDownTimer = new CountDownTimer( 180*1000,1000){
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
//        selected = QuestionsAdapter.MyViewHolder.getAnswers();
        for(int i =0;i < answersList.size();i++){
            if( selected.get(i).trim().toLowerCase().equals(answersList.get(i).trim().toLowerCase()) ){
                this.score += 5;
            }
            else{
                this.score -= 1;
            }
        }
        progressBar3.setVisibility(View.INVISIBLE);
        Intent intent = new Intent(QuestionsActivity.this,StudentsPortalActivity.class);
        Log.d(TAG,"score: "+ score);
        intent.putExtra("score", score);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
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