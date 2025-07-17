package com.saveetha.studyplanner.api;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {
    @FormUrlEncoded
    @POST("study_planner/login.php")
    Call<LoginResponse> loginUser(
        @Field("email") String email,
        @Field("password") String password
    );

    @FormUrlEncoded
    @POST("study_planner/sign_up.php")
    Call<LoginResponse> registerUser(
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password,
            @Field("confirm_password") String confirmPassword
    );
    @FormUrlEncoded
    @POST("study_planner/send_otp.php")
    Call<OtpResponse> sendOtp(@Field("email") String email);

    @FormUrlEncoded
    @POST("study_planner/forgot_password.php")
    Call<ForgotPasswordResponse> resetPassword(
            @Field("email") String email,
            @Field("otp") String otp,
            @Field("new_password") String newPassword,
            @Field("confirm_password") String confirmPassword
    );


    @Multipart
    @POST("study_planner/Change_password.php")
    Call<LoginResponse> changePassword(
            @Part("user_id") RequestBody userId,
            @Part("old_password") RequestBody oldPassword,
            @Part("new_password") RequestBody newPassword,
            @Part("confirm_password") RequestBody confirmPassword
    );



}
