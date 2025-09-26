package com.simats.studyplanner;

public class Task {
    private int id;
    private String task;
    private String description;
    private String date;
    private String time;
    private String priority;
    private String status;
    private int user_id;


    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    // Getters
    public String getTask() { return task; }
    public String getDescription() { return description; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getPriority() { return priority; }
    public String getStatus() { return status; }
}

