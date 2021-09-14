package com.example.myapplication;

public class Progress {
    int questions,correct,wrong,answered,unanswered,score;

    public Progress() {
    }

    public Progress(int questions, int correct, int wrong, int answered, int unanswered, int score) {
        this.questions = questions;
        this.correct = correct;
        this.wrong = wrong;
        this.answered = answered;
        this.unanswered = unanswered;
        this.score = score;
    }

    public int getQuestions() {
        return questions;
    }

    public void setQuestions(int questions) {
        this.questions = questions;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public int getWrong() {
        return wrong;
    }

    public void setWrong(int wrong) {
        this.wrong = wrong;
    }

    public int getAnswered() {
        return answered;
    }

    public void setAnswered(int answered) {
        this.answered = answered;
    }

    public int getUnanswered() {
        return unanswered;
    }

    public void setUnanswered(int unanswered) {
        this.unanswered = unanswered;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
