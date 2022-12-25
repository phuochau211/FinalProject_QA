package com.example.question_answer_app;

public class Question {
    int imgAvata;
    String nameP, subject, question;
    double rating;

    public Question(int imgAvata, String nameP, String subject, String question, double rating) {
        this.imgAvata = imgAvata;
        this.nameP = nameP;
        this.subject = subject;
        this.question = question;
        this.rating = rating;
    }

    public int getImgAvata() {
        return imgAvata;
    }

    public void setImgAvata(int imgAvata) {
        this.imgAvata = imgAvata;
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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
