package com.saveetha.studyplanner.api;

import com.saveetha.studyplanner.StudyMaterial;

import java.util.List;

public class MaterialResponse {
    private boolean status;
    private String message;
    private List<StudyMaterial> data;

    public boolean isStatus() { return status; }
    public String getMessage() { return message; }
    public List<StudyMaterial> getData() { return data; }
}
