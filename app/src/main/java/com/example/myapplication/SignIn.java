package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends Fragment implements View.OnClickListener{
    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    EditText email;
    EditText password;
    Button signup;
    Button loginNow;
    TextView login_signup,textView,Login;
    View view;
    FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        mAuth = FirebaseAuth.getInstance();
        Login = view.findViewById(R.id.login_link);
        Login.setOnClickListener(this::onClick);
        email = view.findViewById(R.id.editTextTextEmailAddressSignUp);
        password = view.findViewById(R.id.editTextTextPasswordSignUp);
        password.setOnClickListener(this::onClick);
        signup = view.findViewById(R.id.buttonSignup);
        signup.setOnClickListener(this::onClick);
        fragmentManager = getActivity().getSupportFragmentManager();
        loginNow = view.findViewById(R.id.loginNow);
        login_signup = view.findViewById(R.id.login_signup);
        textView = view.findViewById(R.id.textView);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if( currentUser != null ){
            Log.d(TAG,"user: "+currentUser);
        }
    }

    private void authentication(String email, String password){
            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                Log.d(TAG, "createUserWithEmail:success "+user.getEmail());
                                updateUI();
                            } else {
                                Toast.makeText(getActivity(),
                                        "The email address is already in use by another account",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    public void updateUI(){
        loginNow.setVisibility(View.VISIBLE);
        email.setVisibility(View.INVISIBLE);
        password.setVisibility(View.INVISIBLE);
        signup.setVisibility(View.INVISIBLE);
        login_signup.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.INVISIBLE);
        Login.setVisibility(View.INVISIBLE);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonSignup:{
                String EMAIL = email.getText().toString();
                Log.d(TAG,"email: "+EMAIL);
                String PASSWORD = password.getText().toString();
//                if(emailValidator(email) && PASSWORD.length() >= 6){
//                    view.findViewById(R.id.passwordWarning).setVisibility(View.INVISIBLE);
//                    view.findViewById(R.id.emailWarning).setVisibility(View.INVISIBLE);
//                    authentication(EMAIL,PASSWORD);
//                }
//                else if( emailValidator(email) == true && PASSWORD.length() < 6 ){
//                    view.findViewById(R.id.passwordWarning).setVisibility(View.VISIBLE);
//                }
                authentication(EMAIL,PASSWORD);
                Log.d(TAG,"password: "+PASSWORD);
            }
            case R.id.login_link:{
                //Navigation.findNavController(view).navigate(R.id.action_signIn_to_homeFragment);
            }
            case R.id.editTextTextPasswordSignUp:{
                emailValidator(email);
            }
            case R.id.loginNow:{
                mAuth.signOut();
                //Navigation.findNavController(view).navigate(R.id.action_signIn_to_homeFragment);
            }
        }
    }
    private boolean emailValidator(EditText email){
        String emailText = email.getText().toString();
        if( !emailText.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){
            view.findViewById(R.id.emailWarning).setVisibility(View.INVISIBLE);
            return true;
        }else{
            view.findViewById(R.id.emailWarning).setVisibility(View.VISIBLE);
            return false;
        }
    }
}