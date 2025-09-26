// ProfileResponse.java
package com.simats.studyplanner.api;

import java.util.List;

public class ProfileResponse {
    public boolean status;
    public String message;
    public List<UserData> data;

    public class UserData {
        public int Id;
        public String name;
        public String email;
        public String phone;
        public String Dept_info;
        public String profile_image;
    }
}
