package com.saveetha.studyplanner;

public class CalendarTaskModel {
    private String title;
    private String description;
    private String time;

    public CalendarTaskModel(String title, String description, String time) {
        this.title = title;
        this.description = description;
        this.time = time;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getTime() { return time; }
}
