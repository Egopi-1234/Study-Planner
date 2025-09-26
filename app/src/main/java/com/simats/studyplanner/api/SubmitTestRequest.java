package com.simats.studyplanner.api;

import java.util.List;

public class SubmitTestRequest {
    private int user_id;
    private String course_name;
    private List<Answer> answers;

    public SubmitTestRequest(int user_id, String course_name, List<Answer> answers) {
        this.user_id = user_id;
        this.course_name = course_name;
        this.answers = answers;
    }

    // Getters
    public int getUser_id() {
        return user_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    // Setters (optional)
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
