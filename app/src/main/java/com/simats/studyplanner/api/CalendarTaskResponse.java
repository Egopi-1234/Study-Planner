package com.simats.studyplanner.api;

import com.simats.studyplanner.CalendarTaskModel;

import java.util.List;

public class CalendarTaskResponse {
    private boolean status;
    private String message;
    private List<CalendarTaskModel> data;

    public boolean isStatus() { return status; }
    public String getMessage() { return message; }
    public List<CalendarTaskModel> getData() { return data; }
}
