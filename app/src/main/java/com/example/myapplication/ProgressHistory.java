package com.example.myapplication;

import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ProgressHistory extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private static final String TAG = "ProgressHistory";
    Button button;
    ArrayList<Progress> singleCheckArray;
    private FirebaseAuth mAuth;
    private boolean norecords;
    ImageView search,lock;
    TextView txt1,txt2;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.norecords = norecords;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_history);

        Toolbar toolbar = findViewById(R.id.toolBar3);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.progress_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        NavigationView navigationView = findViewById(R.id.progress_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        singleCheckArray = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();

        search = findViewById(R.id.imageView);
        lock = findViewById(R.id.lockIcon);
        txt1 = findViewById(R.id.textView10);
        txt2 = findViewById(R.id.textView13);
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
                if( singleCheckArray.size() > 1 ){
                    UpdateUIOnRecordFound();
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {}
        });
    }

    private void UpdateUIOnRecordFound(){
        search.setVisibility(View.INVISIBLE);
        lock.setVisibility(View.INVISIBLE);
        txt1.setVisibility(View.INVISIBLE);
        txt2.setVisibility(View.INVISIBLE);
        button.setFocusable(false);
        button.setVisibility(View.INVISIBLE);
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
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}