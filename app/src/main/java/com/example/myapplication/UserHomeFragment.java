package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.Navigation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

public class UserHomeFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    private FirebaseAuth mAuth;
    TextView textView;
    Button logout;
    View VIEW;
    public UserHomeFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
//        FirebaseUser user = mAuth.getCurrentUser();
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("adminUsers");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
//                    if( user.getEmail() == snapshot.getValue().toString() ){
//                        Intent intent = new Intent(getActivity(),TeachersPortalActivity.class);
//                        getActivity().startActivity(intent);
//                    }
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
        View view= inflater.inflate(R.layout.fragment_user_home, container, false);
        VIEW = view;
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
        if(currentUser != null && currentUser.isEmailVerified()){
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