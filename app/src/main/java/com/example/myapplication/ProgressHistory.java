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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ProgressHistory extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private static final String TAG = "ProgressHistory";
    Button button;
    ArrayList<Progress> singleCheckArray;
    private ArrayList<Progress> list;
    private ProgressHistoryAdapter adapter;
    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private boolean norecords;
    ImageView search,lock;
    TextView txt1,txt2;
    private DrawerLayout drawer;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.norecords = norecords;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_history);

        mAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = findViewById(R.id.toolBar3);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.progress_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        NavigationView navigationView = findViewById(R.id.progress_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        singleCheckArray = new ArrayList<>();

        search = findViewById(R.id.imageView);
        lock = findViewById(R.id.lockIcon);
        txt1 = findViewById(R.id.textView10);
        txt2 = findViewById(R.id.textView13);
        progressBar = findViewById(R.id.progressBarTop);
        button = findViewById(R.id.seeQuizBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProgressHistory.this,StudentsPortalActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        nullInitializedUserCheck();
        toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            Intent intent = new Intent(ProgressHistory.this, StudentsPortalActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void nullInitializedUserCheck(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                singleCheckArray.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Progress progress = dataSnapshot.getValue(Progress.class);
                    singleCheckArray.add(progress);
                }
                if( singleCheckArray.size() >= 1 ){
                    //showRecyclerView(reference);
                    Intent intent = new Intent(ProgressHistory.this,ProgressRecyclerviewActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else{
                    UpdateUIOnRecordFound();
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {}
        });
    }

    private void UpdateUIOnRecordFound(){
        search.setVisibility(View.VISIBLE);
        lock.setVisibility(View.VISIBLE);
        txt1.setVisibility(View.VISIBLE);
        txt2.setVisibility(View.VISIBLE);
        //button.setFocusable(false);
        button.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                mAuth.signOut();
                Intent intent = new Intent(ProgressHistory.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.quizes:
                intent = new Intent(ProgressHistory.this,StudentsPortalActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.quizHistory:
                intent = new Intent(ProgressHistory.this,ProgressHistory.class);
                startActivity(intent);
                break;
            case R.id.resetPassword:
                resetPassword(mAuth.getCurrentUser().getEmail());
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
                            Toast.makeText(ProgressHistory.this,"Email sent.Check Inbox",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(ProgressHistory.this,"Email not registered.Signup first",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}