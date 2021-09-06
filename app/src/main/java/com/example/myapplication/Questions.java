package com.example.myapplication;

import android.content.Intent;

public class Questions {
    private String statement;
    private String a1;
    private String a2;
    private String a3;
    private String a4;
    private String correct;

    public Questions(){

    }

    public Questions(String statement, String option1, String option2, String option3, String option4, String answer) {
        this.statement = statement;
        this.a1 = option1;
        this.a2 = option2;
        this.a3 = option3;
        this.a4 = option4;
        this.correct = answer;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public String getA1() {
        return a1;
    }

    public void setA1(String a1) {
        this.a1 = a1;
    }

    public String getA2() {
        return a2;
    }

    public void setA2(String a2) {
        this.a2 = a2;
    }

    public String getA3() {
        return a3;
    }

    public void setA3(String a3) {
        this.a3 = a3;
    }

    public String getA4() {
        return a4;
    }

    public void setA4(String a4) {
        this.a4 = a4;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }
}
