package com.saveetha.studyplanner.api;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class DeleteTaskResponse {
    @SerializedName("status")
    public boolean status;

    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public List<TaskData> data;

    public static class TaskData {
        @SerializedName("id")
        public int id;

        @SerializedName("name")
        public String name;

        @SerializedName("description")
        public String description;

        @SerializedName("Date")
        public String date;

        @SerializedName("time")
        public String time;

        @SerializedName("Priority")
        public String priority;

        @SerializedName("user_Id")
        public int userId;

        @SerializedName("status")
        public String status;
    }
}
