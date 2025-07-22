package com.saveetha.studyplanner.api;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
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


    @FormUrlEncoded
    @POST("study_planner/Change_password.php")
    Call<LoginResponse> changePassword(
            @Field("user_id") String userId,
            @Field("old_password") String oldPassword,
            @Field("new_password") String newPassword,
            @Field("confirm_password") String confirmPassword
    );


    @Multipart
    @POST("study_planner/view_profile.php")
    Call<ProfileResponse> viewProfile(
            @Part("user_id") RequestBody userId
    );


    @Multipart
    @POST("study_planner/update_profile.php")
    Call<UpdateProfileResponse> updateProfile(
            @Part("user_id") RequestBody userId,
            @Part("name") RequestBody name,
            @Part("email") RequestBody email,
            @Part("phone") RequestBody phone,
            @Part("Dept_info") RequestBody dept,
            @Part MultipartBody.Part profile_image  // can be null
    );

    @Multipart
    @POST("study_planner/Add_tasks.php")
    Call<AddTaskResponse> addTask(
            @Part("user_id") RequestBody userId,
            @Part("task") RequestBody task,
            @Part("description") RequestBody description,
            @Part("date") RequestBody date,
            @Part("time") RequestBody time,
            @Part("priority") RequestBody priority
    );

    @Multipart
    @POST("study_planner/view_tasks.php/all")
    Call<TaskResponse> getTasks(
            @Part("user_id") RequestBody userId
    );

    @FormUrlEncoded
    @POST("study_planner/view_materials.php")
    Call<MaterialResponse> getStudyMaterials(@Field("user_id") int userId);

    @Multipart
    @POST("study_planner/create_material.php")
    Call<UploadMaterialResponse> uploadMaterial(
            @Part("name") RequestBody name,
            @Part("subject") RequestBody subject,
            @Part("due_date") RequestBody dueDate,
            @Part("due_time") RequestBody dueTime,
            @Part("user_id") RequestBody userId,
            @Part MultipartBody.Part file
    );

    @Multipart
    @POST("study_planner/date_tasks_view.php")
    Call<CalendarTaskResponse> getTasksByDate(
            @Part("user_id") RequestBody userId,
            @Part("date") RequestBody date
    );

        @FormUrlEncoded
        @POST("study_planner/edit_tasks.php")
        Call<ApiResponse> updateTask(@FieldMap Map<String, String> map);

    @Multipart
    @POST("update_material.php")
    Call<ResponseBody> updateMaterial(
            @Part("material_id") RequestBody materialId,
            @Part("material_name") RequestBody materialName,
            @Part("subject") RequestBody subject,
            @Part("due_date") RequestBody dueDate,
            @Part("due_time") RequestBody dueTime,
            @Part MultipartBody.Part file // optional PDF file
    );

//    @FormUrlEncoded
//    @POST("study_planner/get_user.php")
//    Call<UpdateProfileResponse> getUserProfile(
//            @Field("user_id") int userId
//    );

}
