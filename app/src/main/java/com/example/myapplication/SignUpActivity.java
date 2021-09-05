package com.example.myapplication;

import android.content.Intent;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    EditText email, Password;
    Button signup;
    TextView loginText;
    private FirebaseAuth mAuth;
    ImageButton passwordtoggle;
    private static final String TAG = "SignUpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        email = findViewById(R.id.editTextTextEmailAddressSignUp);
        Password = findViewById(R.id.editTextTextPasswordSignUp);
        signup = findViewById(R.id.buttonSignup);
        signup.setOnClickListener(this::onClick);
        loginText = findViewById(R.id.login_link);
        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        passwordtoggle = findViewById(R.id.passwordToggle);
        passwordtoggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passwordtoggle.getTag().equals("show")) {
                    Password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    passwordtoggle.setImageResource(R.drawable.baseline_visibility_off_24);
                    passwordtoggle.setTag("hide");
                }else{
                    Password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    passwordtoggle.setImageResource(R.drawable.baseline_visibility_24);
                    passwordtoggle.setTag("show");
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonSignup: {
                if( emailValidator(email,view) && Password.getText().toString().length() >=6){
                    authentication(email.getText().toString(),Password.getText().toString());
                }
                else {
                    Toast.makeText(SignUpActivity.this,"Invalid email address or password",Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void authentication(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.d(TAG, "createUserWithEmail:success" + user.getDisplayName());
//                            mAuth.signOut();
                            Toast.makeText(SignUpActivity.this,"Signup success",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUpActivity.this, EmailValidationActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.Try Logging In",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private boolean emailValidator(EditText email,View view){
        String emailText = email.getText().toString();
        if( !emailText.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){
            return true;
        }else{
            return false;
        }
    }
}