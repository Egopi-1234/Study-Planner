package com.simats.studyplanner.api;

public class AddTaskResponse {
    public boolean status;
    public String message;
    public TaskData data;

    public static class TaskData {
        public int id;
        public String task, description, date, time, priority;
        public String user_id;
    }
}
