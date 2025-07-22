package com.saveetha.studyplanner.api;

public class MaterialUpdateRequest {
    private int material_id;
    private String material_name;
    private String subject;
    private String due_date;
    private String due_time;

    public MaterialUpdateRequest(int material_id, String material_name, String subject, String due_date, String due_time) {
        this.material_id = material_id;
        this.material_name = material_name;
        this.subject = subject;
        this.due_date = due_date;
        this.due_time = due_time;
    }
}
