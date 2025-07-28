package com.saveetha.studyplanner.api;

import com.google.gson.annotations.SerializedName;

public class DeleteMaterialResponse {

    @SerializedName("status")
    private boolean status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private MaterialData data;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public MaterialData getData() {
        return data;
    }

    public class MaterialData {
        @SerializedName("id")
        private int id;

        @SerializedName("name")
        private String name;

        @SerializedName("subject")
        private String subject;

        @SerializedName("due_date")
        private String dueDate;

        @SerializedName("due_time")
        private String dueTime;

        @SerializedName("file_path")
        private String filePath;

        @SerializedName("user_id")
        private int userId;

        @SerializedName("created_at")
        private String createdAt;

        // Getters for all fields...
        public int getId() { return id; }
        public String getName() { return name; }
        public String getSubject() { return subject; }
        public String getDueDate() { return dueDate; }
        public String getDueTime() { return dueTime; }
        public String getFilePath() { return filePath; }
        public int getUserId() { return userId; }
        public String getCreatedAt() { return createdAt; }
    }
}
