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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import org.jetbrains.annotations.NotNull;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Locale;

public class StudentsPortalActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ExitAppDialog.ExitAppListener {
    private FirebaseAuth mAuth;
    private DrawerLayout drawer;
    private CategoriesAdapter adapter;
    private RecyclerView recyclerView;
    private static final String TAG = "StudentsPortalActivity";
    ArrayList<String> list;
    Button button,GateButton;
    FirebaseUser currentUser;
    TextView scoreView;
    Intent intent;
    int score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_portal);

        Toolbar toolbar = findViewById(R.id.toolBar2);
        setSupportActionBar(toolbar);
        //scoreView = findViewById(R.id.scoreView);
        Intent intent = this.getIntent();
        score = intent.getIntExtra("score",0);
        String txt = ""+score+"";

        drawer = findViewById(R.id.students_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        NavigationView navigationView = findViewById(R.id.students_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView userEmail = headerView.findViewById(R.id.user_email);
        userEmail.setText(currentUser.getEmail());

        recyclerView = findViewById(R.id.categoryRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new CategoriesAdapter(this,list);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new CategoriesAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(StudentsPortalActivity.this,QuestionsActivity.class);
                intent.putExtra("category",list.get(position));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("categories");
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    list.add(dataSnapshot.getValue().toString());
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            ExitAppDialog exitAppDialog = new ExitAppDialog();
            exitAppDialog.show(getSupportFragmentManager(),"exit app");
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
            case R.id.quizes:
                intent = new Intent(StudentsPortalActivity.this,StudentsPortalActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
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