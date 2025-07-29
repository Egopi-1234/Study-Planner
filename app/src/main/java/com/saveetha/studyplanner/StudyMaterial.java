package com.saveetha.studyplanner;

// StudyMaterial.java

public class StudyMaterial {
    private int id;
    private String name;
    private String subject;
    private String due_date;
    private String due_time;
    private String file_path;
    private String created_at;

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getSubject() { return subject; }
    public String getDueDate() { return due_date; }
    public String getDueTime() { return due_time; }
    public String getFilePath() { return file_path; }
    public String getCreatedAt() { return created_at; }
}

