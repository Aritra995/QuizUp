package com.example.myapplication;

public class Feedback {
    String rating;
    String feedbackTxt;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    String recommend;
    String userId;

    public Feedback(String rating, String feedbackTxt, String recommend, String userId) {
        this.rating = rating;
        this.feedbackTxt = feedbackTxt;
        this.recommend = recommend;
        this.userId = userId;
    }

    public Feedback() {
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getFeedbackTxt() {
        return feedbackTxt;
    }

    public void setFeedbackTxt(String feedbackTxt) {
        this.feedbackTxt = feedbackTxt;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

}
