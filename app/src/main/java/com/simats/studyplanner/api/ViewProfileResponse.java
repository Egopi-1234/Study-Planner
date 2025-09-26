package com.simats.studyplanner.api;

import java.util.List;

public class ViewProfileResponse {
    private boolean status;
    private String message;
    private List<UserData> data;

    public boolean isStatus() { return status; }
    public String getMessage() { return message; }
    public List<UserData> getData() { return data; }

    public static class UserData {
        private int Id;
        private String name;
        private String email;
        private String password;
        private String phone;
        private String Dept_info;
        private String profile_image;
        private String device_token;

        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getProfileImage() { return profile_image; }
    }
}
