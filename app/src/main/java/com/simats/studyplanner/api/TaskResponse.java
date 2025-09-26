package com.simats.studyplanner.api;

import java.util.List;                        // List import
import com.simats.studyplanner.Task;       // Task class import

public class TaskResponse {
    private boolean status;
    private String message;
    private String view;
    private List<Task> data;

    public boolean isStatus() {
        return status;
    }

    public List<Task> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public String getView() {
        return view;
    }
}
