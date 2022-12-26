package com.example.question_answer_app;

public class Question {
    int imgAvatar;
    String nameP, subject, question,rating;

    public Question(){}

    public Question(String nameP, String subject, String question) {
        this.nameP = nameP;
        this.subject = subject;
        this.question = question;
    }
    public Question(String nameP, String subject, String question, String rating) {
        this.nameP = nameP;
        this.subject = subject;
        this.question = question;
        this.rating = rating;
    }

    public int getImgAvata() {
        return imgAvatar;
    }

    public void setImgAvata(int imgAvata) {
        this.imgAvatar = imgAvata;
    }

    public String getNameP() {
        return nameP;
    }

    public void setNameP(String nameP) {
        this.nameP = nameP;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
