package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.Navigation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserHomeFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "MainActivity";

    private String mParam1;
    private String mParam2;
    FirebaseAuth mAuth;
    TextView textView;
    Button logout;
    View VIEW;
    public UserHomeFragment() {
        // Required empty public constructor
    }
    public static UserHomeFragment newInstance(String param1, String param2) {
        UserHomeFragment fragment = new UserHomeFragment();
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
        View view= inflater.inflate(R.layout.fragment_user_home, container, false);
        VIEW = view;
        mAuth = FirebaseAuth.getInstance();
        textView = view.findViewById(R.id.user_name);
        logout = view.findViewById(R.id.signout);
        logout.setOnClickListener(this::onClick);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Log.d(TAG,"User: "+currentUser);
        if(currentUser != null){
            textView.setText(currentUser.getEmail());
        }
        else{
            Navigation.findNavController(VIEW).navigate(R.id.action_userHomeFragment_to_homeFragment);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signout:{
                mAuth.signOut();
                Navigation.findNavController(view).navigate(R.id.action_userHomeFragment_to_homeFragment);
            }
        }
    }
}