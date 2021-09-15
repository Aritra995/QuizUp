package com.example.myapplication;

public class Progress {
    String questions,correct,wrong,answered,unanswered,score;
    String category,date;

    public Progress() {
    }

    public Progress(String questions, String correct, String wrong, String answered, String unanswered, String score, String category, String date) {
        this.questions = questions;
        this.correct = correct;
        this.wrong = wrong;
        this.answered = answered;
        this.unanswered = unanswered;
        this.score = score;
        this.category = category;
        this.date = date;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public String getWrong() {
        return wrong;
    }

    public void setWrong(String wrong) {
        this.wrong = wrong;
    }

    public String getAnswered() {
        return answered;
    }

    public void setAnswered(String answered) {
        this.answered = answered;
    }

    public String getUnanswered() {
        return unanswered;
    }

    public void setUnanswered(String unanswered) {
        this.unanswered = unanswered;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
