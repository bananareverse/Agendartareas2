package com.ida.actividad03android2026;

public class Task {
    private int id;
    private String title;
    private String date;
    private boolean isCompleted;

    public Task(int id, String title, String date, boolean isCompleted) {
        this.id = id;
        this.title = title;    this.date = date;
        this.isCompleted = isCompleted;
    }

    public String getTitle(){
        return title;
    }
    public String getDate(){
        return date;
    }
    public boolean isCompleted(){
        return isCompleted;
    }
    public void setCompleted(boolean completed){
        isCompleted = completed;
    }

}

