package com.example.myapplication;

import android.nfc.Tag;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.google.firebase.database.*;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference myref = firebaseDatabase.getReference();
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                TextView textView = findViewById(R.id.text2);
                textView.setText(value.toString());
                Log.d(TAG,"Value is: "+value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG,"Failed to read value: ",error.toException());
            }
        });
//        myref.setValue("Hello from java application");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}