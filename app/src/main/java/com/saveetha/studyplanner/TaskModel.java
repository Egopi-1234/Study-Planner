package com.saveetha.studyplanner;

public class TaskModel {
    String title, time, date, priority;

    public TaskModel(String title, String time, String date, String priority) {
        this.title = title;
        this.time = time;
        this.date = date;
        this.priority = priority;
    }

    public String getTitle() { return title; }
    public String getTime() { return time; }
    public String getDate() { return date; }
    public String getPriority() { return priority; }
}
