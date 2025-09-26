package com.simats.studyplanner;

public class CalendarTaskModel {
    private int id;
    private String task;
    private String description;
    private String date;
    private String time;
    private String priority;
    private String status;

    public CalendarTaskModel() {}

    public CalendarTaskModel(int id, String task, String description, String date, String time, String priority, String status) {
        this.id = id;
        this.task = task;
        this.description = description;
        this.date = date;
        this.time = time;
        this.priority = priority;
        this.status = status;
    }

    public int getId() { return id; }
    public String getTask() { return task; }
    public String getDescription() { return description; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getPriority() { return priority; }
    public String getStatus() { return status; }
}
