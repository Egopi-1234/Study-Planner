package com.saveetha.studyplanner;

public class UpcomingTaskModel {
    private String title;
    private String time;

    public UpcomingTaskModel(String title, String time) {
        this.title = title;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }
}
