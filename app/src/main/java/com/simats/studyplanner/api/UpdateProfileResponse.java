package com.simats.studyplanner.api;

import java.util.List;

public class UpdateProfileResponse {
    private boolean status;
    private String message;
    private List<UserData> data;

    public boolean isStatus() { return status; }
    public String getMessage() { return message; }
    public List<UserData> getData() { return data; }

    public static class UserData {
        private int id;
        private String name;
        private String email;
        private String phone;
        private String Dept_info;
        private String profile_image;

        public int getId() { return id; }
        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getPhone() { return phone; }
        public String getDept_info() { return Dept_info; }
        public String getProfile_image() { return profile_image; }
    }
}
