package com.example.myapplication;

import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
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
import com.squareup.picasso.Picasso;
import org.jetbrains.annotations.NotNull;

public class TeachersPortalActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mAuth;
    private DrawerLayout drawer;
    private static final String TAG = "TeachersPortalActivity";
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers_portal);

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView userEmail = headerView.findViewById(R.id.user_email);
        userEmail.setText(currentUser.getEmail());
//        ImageView userDp = headerView.findViewById(R.id.userDp);
//        Log.d(TAG,"url: "+currentUser.getPhotoUrl());
//        Picasso.get().load(currentUser.getPhotoUrl()).into(userDp);
        toggle.syncState();
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
                Intent intent = new Intent(TeachersPortalActivity.this,MainActivity.class);
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
                            Toast.makeText(TeachersPortalActivity.this,"Email sent.Check Inbox",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(TeachersPortalActivity.this,"Email not registered.Signup first",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}