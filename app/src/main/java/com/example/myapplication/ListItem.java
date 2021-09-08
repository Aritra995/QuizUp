package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;

public class ListItem {
    public String statement;
    public String a1;
    public String a2;
    public String a3;
    public String a4;

    public ListItem(String statement, String a1,String a2,String a3, String a4){
        this.statement = statement;
        this.a1 = a1;
        this.a2 = a2;
        this.a3 = a3;
        this.a4 = a4;
    }
}
