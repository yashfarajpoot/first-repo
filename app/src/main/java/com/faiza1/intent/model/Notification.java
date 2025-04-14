package com.faiza1.intent.model;

public class Notification {
    public String title,body,date;
    public Notification() {}
    public Notification(String title, String body, String date) {
        this.title = title;
        this.body = body;
        this.date = date;
    }
    public String getTitle() { return title; }
    public String getBody() { return body; }
    public String getDate() { return date; }

    public void setTitle(String title) { this.title = title; }
    public void setBody(String body) { this.body = body; }
    public void setDate(String date) { this.date = date; }
}



