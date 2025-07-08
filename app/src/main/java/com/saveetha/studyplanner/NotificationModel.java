package com.saveetha.studyplanner;


public class NotificationModel {
    private String title, subtitle, content, dueDate;

    public NotificationModel(String title, String subtitle, String content, String dueDate) {
        this.title = title;
        this.subtitle = subtitle;
        this.content = content;
        this.dueDate = dueDate;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getContent() {
        return content;
    }

    public String getDueDate() {
        return dueDate;
    }
}
