package com.example.myapplication;

import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference myref = firebaseDatabase.getReference();
    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    private String EMAIL;
    private String PASSWORD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
//                TextView textView = findViewById(R.id.text2);
//                textView.setText(value.toString());
                Log.d(TAG,"Value is: "+value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG,"Failed to read value: ",error.toException());
            }
        });
//        myref.setValue("Hello from java application");
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Button login = findViewById(R.id.button);
//        login.setOnClickListener(this::onClick);
//        Button logout = findViewById(R.id.button2);
//        logout.setOnClickListener(this::onClick);
    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if( currentUser != null ){
//            showText(currentUser);
//            hideUpdateUI(currentUser);
//            showLogout();
//        }
//
//    }
//    private void showText(FirebaseUser user){
//        TextView textView = findViewById(R.id.text2);
//        textView.setText("Logged in as "+ user.getDisplayName());
//    }
//    private void hideText(){
//        TextView textView = findViewById(R.id.text2);
//        textView.setVisibility(View.INVISIBLE);
//    }
//    private void showLogout(){
//        Button logout = findViewById(R.id.button2);
//        logout.setVisibility(View.VISIBLE);
//    }
//    private void hideLogout(){
//        Button logout = findViewById(R.id.button2);
//        logout.setVisibility(View.INVISIBLE);
//    }
//    private void showUpdateUI(){
//        EditText email = findViewById(R.id.editTextTextEmailAddress);
//        email.setVisibility(View.VISIBLE);
//        EditText password = findViewById(R.id.editTextTextPassword);
//        password.setVisibility(View.VISIBLE);
//        Button login = findViewById(R.id.button);
//        login.setVisibility(View.VISIBLE);
//    }
//    private void hideUpdateUI(FirebaseUser user){
//        EditText email = findViewById(R.id.editTextTextEmailAddress);
//        email.setVisibility(View.INVISIBLE);
//        EditText password = findViewById(R.id.editTextTextPassword);
//        password.setVisibility(View.INVISIBLE);
//        Button login = findViewById(R.id.button);
//        login.setVisibility(View.INVISIBLE);
//    }
//    private void signOut(){
//        mAuth.signOut();
//        showUpdateUI();
//        hideText();
//        hideLogout();
//    }
//
//    private void authentication(String email,String password){
//        mAuth.createUserWithEmailAndPassword(email,password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "createUserWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
////                            updateUI(user);
//                            TextView textView = findViewById(R.id.text2);
//                            textView.setText("Logged in as "+user.getDisplayName());
//                            hideUpdateUI(user);
//                            showLogout();
//
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                            Toast.makeText(MainActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
////                            updateUI(null);
//                        }
//                    }
//                });
//    }

    @Override
    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.button:{
//                EditText email = findViewById(R.id.editTextTextEmailAddress);
//                EMAIL =  email.getText().toString();
//                EditText password = findViewById(R.id.editTextTextPassword);
//                PASSWORD= password.getText().toString();
////                Button login = findViewById(R.id.button);
//                authentication(EMAIL,PASSWORD);
////                login.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View view) {
////                        authentication(EMAIL,PASSWORD,mAuth);
////                    }
////                });
//            }
//            case R.id.button2:{
//                signOut();
//            }
//        }

    }
}