package com.saveetha.studyplanner.api;

import com.google.gson.annotations.SerializedName;

public class EditMaterialResponse {

    @SerializedName("status")
    public boolean status;

    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public Data data;

    public class Data {
        @SerializedName("id")
        public String id;

        @SerializedName("name")
        public String name;

        @SerializedName("subject")
        public String subject;

        @SerializedName("due_date")
        public String dueDate;

        @SerializedName("due_time")
        public String dueTime;

        @SerializedName("file_path")
        public String filePath;
    }
}
