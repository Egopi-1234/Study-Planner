package com.simats.studyplanner.api;

import java.util.List;

public class UploadMaterialResponse {
    public boolean status;
    public String message;
    public List<MaterialData> data;

    public static class MaterialData {
        public int id;
        public String name;
        public String subject;
        public String due_date;
        public String due_time;
        public String file_path;
    }
}
