package com.simats.studyplanner.api;

public class OtpResponse {
    public boolean status;
    public String message;
    public Data data;

    public static class Data {
        public int otp;
    }
}