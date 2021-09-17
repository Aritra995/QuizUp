package com.example.myapplication;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.WindowManager;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "MainActivity";
    private DatabaseReference adminReference;
    FirebaseAuth mAuth;
    EditText email;
    EditText password;
    Button login;
    ProgressBar progressBar;
    TextView textView,textView_title,bottomLine,forgotPassword;
    private String mParam1;
    private String mParam2;
    private View VIEW;
    FrameLayout frameLayout;
    private static boolean loggedIn;
    private boolean admin;
    ImageButton passwordtoggle;
    List<String> adminList = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        VIEW = view;
        mAuth = FirebaseAuth.getInstance();
        adminReference = FirebaseDatabase.getInstance().getReference().child("adminUsers");
        textView = view.findViewById(R.id.Login_tosignup);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),SignUpActivity.class);
                getActivity().startActivity(intent);
            }
        });
        forgotPassword = view.findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( emailValidator(email,view) ){
                    resetPassword(email.getText().toString());
                }else{
                    Toast.makeText(getActivity(),"Enter a valid email address",Toast.LENGTH_LONG).show();
                }

            }
        });
        textView_title = view.findViewById(R.id.textView5);
        bottomLine = view.findViewById(R.id.textView);
        email = view.findViewById(R.id.loginEmail);
        password = view.findViewById(R.id.loginPassword);
        login = view.findViewById(R.id.loginButton);
        login.setOnClickListener(this::onClick);
        setAdmin(false);
        progressBar = view.findViewById(R.id.progressBar);
        passwordtoggle = view.findViewById(R.id.passwordToggle);
        passwordtoggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passwordtoggle.getTag().equals("show")) {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    passwordtoggle.setImageResource(R.drawable.baseline_visibility_off_24);
                    passwordtoggle.setTag("hide");
                }else{
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    passwordtoggle.setImageResource(R.drawable.baseline_visibility_24);
                    passwordtoggle.setTag("show");
                }
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null && currentUser.isEmailVerified()){
            disable();
            adminCheck();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loginButton:{
                if( emailValidator(email,view) && password.getText().toString().length() >=6){
                    loginAuthentication(email.getText().toString(),password.getText().toString());
                }
                else {
                    Toast.makeText(getActivity(),"Invalid email address or password",Toast.LENGTH_LONG).show();
                }
            }
            case R.id.Login_tosignup:{
                //Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_signIn);
            }
        }

    }
    private void loginAuthentication(String email,String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if( user.isEmailVerified() ){
                                adminCheck();
                            }else{
                                Intent intent =new Intent(getActivity(),EmailValidationActivity.class);
                                getActivity().startActivity(intent);
                            }

                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getActivity(), "Authentication failed.Signup or check password",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void adminCheck(){
        progressBar.setVisibility(View.VISIBLE);
        adminReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adminList.clear();
                FirebaseUser currentUser = mAuth.getCurrentUser();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    if( currentUser.getUid().equals(dataSnapshot.getValue().toString()) ){
                        setAdmin(true);
                        Log.d(TAG,"Something from here");
                        progressBar.setVisibility(View.INVISIBLE);
                        Intent intent = new Intent(getActivity(),TeachersPortalActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        getContext().startActivity(intent);
                    }
                }
                if( isAdmin() == false ) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Log.d(TAG, "Redirecting to non-admin home screen");
                    Intent intent = new Intent(getActivity(),StudentsPortalActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    getActivity().startActivity(intent);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void redirect(String userType){
        switch (userType){
            case "student":
                Intent intent = new Intent(getActivity(),StudentsPortalActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
    private void disable(){
        email.setFocusable(false);
        password.setFocusable(false);
        login.setFocusable(false);
    }

    private boolean emailValidator(EditText email,View view){
        String emailText = email.getText().toString();
        if( !emailText.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){
            return true;
        }else{
            return false;
        }
    }
    private void resetPassword(String emailAddress){
        mAuth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                            Toast.makeText(getActivity(),"Email sent.Check Inbox",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getActivity(),"Email not registered.Signup first",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    private void setLoggedIn(boolean value) {
        this.loggedIn = value;
    }

    private boolean getLoggedIn() {
        return loggedIn;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}