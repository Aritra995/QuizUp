package com.example.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Patterns;
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
public class HomeFragment extends Fragment implements View.OnClickListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "MainActivity";

    FirebaseAuth mAuth;
    EditText email;
    EditText password;
    Button login;
    TextView textView,textView_title,bottomLine;
    private String mParam1;
    private String mParam2;
    private View VIEW;
    FrameLayout frameLayout;
    private static boolean loggedIn;

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
        textView = view.findViewById(R.id.Login_tosignup);
        textView.setOnClickListener(this::onClick);
        textView_title = view.findViewById(R.id.textView5);
        bottomLine = view.findViewById(R.id.textView);
        email = view.findViewById(R.id.loginEmail);
        password = view.findViewById(R.id.loginPassword);
        login = view.findViewById(R.id.loginButton);
        login.setOnClickListener(this::onClick);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Navigation.findNavController(VIEW).navigate(R.id.action_homeFragment_to_userHomeFragment);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loginButton:{
                if( login.getText().toString().length() == 6 ){
                    // signup logic
                    login.setText("LOGIN");
                    textView_title.setText("LOGIN");
                    textView.setText("Sign Up");
                    bottomLine.setText("Don't have an account ?");
                }
                else{
                    if( emailValidator(email,view) && password.getText().toString().length() >=6){
                        loginAuthentication(email.getText().toString(),password.getText().toString());
                    }
                    else {
                        //view.findViewById(R.id.textView3).setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(),"Invalid email address or password",Toast.LENGTH_LONG).show();
                    }
                }
            }
            case R.id.Login_tosignup:{
                //Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_signIn);
                login.setText("SIGNUP");
                //login.setId(Integer.parseInt("signupButton"));
                textView_title.setText("SIGNUP");
                //textView_title.setId(Integer.parseInt("signupTitle"));
                textView.setText("Log In");
                //textView.setId(Integer.parseInt("signupLink"));
                bottomLine.setText("Already have an account ?");
                //bottomLine.setId(Integer.parseInt("signupLine"));
            }
        }

    }
    private void loginAuthentication(String email,String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Navigation.findNavController(VIEW).navigate(R.id.action_homeFragment_to_userHomeFragment);

                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private boolean emailValidator(EditText email,View view){
        String emailText = email.getText().toString();
        if( !emailText.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){
            //view.findViewById(R.id.textView2).setVisibility(View.INVISIBLE);
            return true;
        }else{
            //view.findViewById(R.id.textView2).setVisibility(View.VISIBLE);
            return false;
        }
    }
    private void setLoggedIn(boolean value) {
        this.loggedIn = value;
    }

    private boolean getLoggedIn() {
        return loggedIn;
    }
}