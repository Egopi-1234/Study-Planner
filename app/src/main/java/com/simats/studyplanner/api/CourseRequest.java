package com.simats.studyplanner.api;

public class CourseRequest {
    private String course_name;
    private String email_id;

    public CourseRequest(String course_name, String email_id) {
        this.course_name = course_name;
        this.email_id = email_id;
    }
}
