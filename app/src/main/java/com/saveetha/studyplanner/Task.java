package com.saveetha.studyplanner;

public class Task {
    private String title;
    private String priority;

    public Task(String title, String priority) {
        this.title = title;
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public String getPriority() {
        return priority;
    }
}
