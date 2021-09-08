package com.example.myapplication;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class QuestionsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    QuestionsAdapter adapter;
    ArrayList<Questions> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
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

    }
}