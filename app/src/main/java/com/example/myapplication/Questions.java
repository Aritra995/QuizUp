package com.example.myapplication;

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

    public String getA1() {
        return a1;
    }


    public String getA2() {
        return a2;
    }


    public String getA3() {
        return a3;
    }


    public String getA4() {
        return a4;
    }


    public String getCorrect() {
        return correct;
    }

}
