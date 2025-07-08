package com.saveetha.studyplanner.models;

public class StudyMaterial {
    private String title;
    private String dateTime;

    public StudyMaterial(String title, String dateTime) {
        this.title = title;
        this.dateTime = dateTime;
    }

    public String getTitle() {
        return title;
    }

    public String getDateTime() {
        return dateTime;
    }
}
