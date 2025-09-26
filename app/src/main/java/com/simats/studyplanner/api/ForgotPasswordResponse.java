package com.simats.studyplanner.api;

public class ForgotPasswordResponse {
    public boolean status;
    public String message;
    public Data data;

    public static class Data {
        public int id;
        public String email;
    }
}