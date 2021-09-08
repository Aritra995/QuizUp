package com.example.myapplication;

import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import org.jetbrains.annotations.NotNull;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Locale;

public class StudentsPortalActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private FirebaseAuth mAuth;
    private DrawerLayout drawer;
    private static final String TAG = "StudentsPortalActivity";
    ArrayList<String> list;
    ArrayAdapter adapter;
    Button button;
    FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_portal);

        Toolbar toolbar = findViewById(R.id.toolBar2);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.students_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        NavigationView navigationView = findViewById(R.id.students_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView userEmail = headerView.findViewById(R.id.user_email);
        userEmail.setText(currentUser.getEmail());

        button = findViewById(R.id.takeQuiz);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentsPortalActivity.this,QuestionsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        toggle.syncState();

//        list = new ArrayList<>();
//        adapter = new ArrayAdapter<String>(this,R.layout.list_questions,list);
//        listView.setAdapter(adapter);

//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("questions").child("General Knowledge");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                list.clear();
//                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
//                    Questions questionsModal = dataSnapshot.getValue(Questions.class);
//                    String txt = "Q1: "+  questionsModal.getStatement()+" a. "+questionsModal.getA1()+"b. "+questionsModal.getA2()
//                            +" c. "+questionsModal.getA3()+" d. "+questionsModal.getA4()+" ans. "+questionsModal.getCorrect();
//                    list.add(questionsModal.getStatement());
//                    list.add(questionsModal.getA1());
//                    list.add(questionsModal.getA2());
//                    list.add(questionsModal.getA3());
//                    list.add(questionsModal.getA4());
//                    Log.d(TAG,txt);
//                }
//                adapter.notifyDataSetChanged();
//            }
//            @Override
//            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                mAuth.signOut();
                Intent intent = new Intent(StudentsPortalActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.resetPassword:
                resetPassword(currentUser.getEmail());
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void resetPassword(String emailAddress){
        mAuth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                            Toast.makeText(StudentsPortalActivity.this,"Email sent.Check Inbox",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(StudentsPortalActivity.this,"Email not registered.Signup first",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}