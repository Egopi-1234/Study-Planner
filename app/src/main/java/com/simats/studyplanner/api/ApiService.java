package com.simats.studyplanner.api;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {
    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> loginUser(
        @Field("email") String email,
        @Field("password") String password
    );

    @FormUrlEncoded
    @POST("sign_up.php")
    Call<LoginResponse> registerUser(
            @Field("name") String name,  // ✅ Fix here
            @Field("email") String email,
            @Field("password") String password,
            @Field("confirm_password") String confirmPassword
    );
    @FormUrlEncoded
    @POST("send_otp.php")
    Call<OtpResponse> sendOtp(@Field("email") String email);

    @FormUrlEncoded
    @POST("forgot_password.php")
    Call<ForgotPasswordResponse> resetPassword(
            @Field("email") String email,
            @Field("otp") String otp,
            @Field("new_password") String newPassword,
            @Field("confirm_password") String confirmPassword
    );


    @FormUrlEncoded
    @POST("Change_password.php")
    Call<LoginResponse> changePassword(
            @Field("user_id") String userId,
            @Field("old_password") String oldPassword,
            @Field("new_password") String newPassword,
            @Field("confirm_password") String confirmPassword
    );


    @Multipart
    @POST("view_profile.php")
    Call<ProfileResponse> viewProfile(
            @Part("user_id") RequestBody userId
    );


    @Multipart
    @POST("update_profile.php")
    Call<UpdateProfileResponse> updateProfile(
            @Part("user_id") RequestBody userId,
            @Part("name") RequestBody name,
            @Part("email") RequestBody email,
            @Part("phone") RequestBody phone,
            @Part("Dept_info") RequestBody dept,
            @Part MultipartBody.Part profile_image // ✅ this can be null or empty
    );


    @Multipart
    @POST("Add_tasks.php")
    Call<AddTaskResponse> addTask(
            @Part("user_id") RequestBody userId,
            @Part("task") RequestBody task,
            @Part("description") RequestBody description,
            @Part("date") RequestBody date,
            @Part("time") RequestBody time,
            @Part("priority") RequestBody priority
    );

    @Multipart
    @POST("view_tasks.php/all")
    Call<TaskResponse> getTasks(
            @Part("user_id") RequestBody userId
    );

    @FormUrlEncoded
    @POST("view_materials.php")
    Call<MaterialResponse> getStudyMaterials(@Field("user_id") int userId);

    @Multipart
    @POST("create_material.php")
    Call<UploadMaterialResponse> uploadMaterial(
            @Part("name") RequestBody name,
            @Part("subject") RequestBody subject,
            @Part("due_date") RequestBody dueDate,
            @Part("due_time") RequestBody dueTime,
            @Part("user_id") RequestBody userId,
            @Part MultipartBody.Part file
    );

    @Multipart
    @POST("date_tasks_view.php")
    Call<CalendarTaskResponse> getTasksByDate(
            @Part("user_id") RequestBody userId,
            @Part("date") RequestBody date
    );

        @FormUrlEncoded
        @POST("edit_tasks.php")
        Call<ApiResponse> updateTask(@FieldMap Map<String, String> map);


    @Multipart
    @POST("edit_material.php")
    Call<EditMaterialResponse> updateMaterial(
            @Part("material_id") RequestBody materialId,
            @Part("user_id") RequestBody userId,
            @Part("name") RequestBody name,
            @Part("subject") RequestBody subject,
            @Part("due_date") RequestBody dueDate,
            @Part("due_time") RequestBody dueTime,
            @Part MultipartBody.Part file
    );

    @Multipart
    @POST("delete_tasks.php")
    Call<DeleteTaskResponse> deleteTask(
            @Part("user_id") RequestBody userId,
            @Part("task_id") RequestBody taskId
    );

    @FormUrlEncoded
    @POST("delete_material.php")
    Call<DeleteMaterialResponse> deleteMaterial(
            @Field("material_id") int materialId,
            @Field("user_id") int userId
    );

    @Multipart
    @POST("update_status.php")
    Call<DeleteTaskResponse> updateTaskStatus(
            @Part("user_id") RequestBody userId,
            @Part("task_id") RequestBody taskId,
            @Part("status") RequestBody status
    );

    @Multipart
    @POST("update_material_status.php")
    Call<DeleteMaterialResponse> updateMaterialStatus(
            @Part("materials_id") RequestBody materialId,
            @Part("status") RequestBody status
    );

    @FormUrlEncoded
    @POST("update_device_token.php")
    Call<Void> updateDeviceToken(
            @Field("user_id") int userId,
            @Field("device_token") String deviceToken
    );

    public interface MCQApi {
        @Headers("Accept: application/json")
        @POST("submit_test.php")
        Call<SubmitTestResponse> submitTest(@Body SubmitTestRequest request);
    }
    // Add this inside ApiService interface
    @Headers("Content-Type: application/json")
    @POST("view_results.php")
    Call<ResponseBody> getResult(@Body RequestBody body);


    @Headers("Content-Type: application/json")
    @POST("enroll.php")
    Call<ApiResponse> enroll(@Body CourseRequest request);

    @Headers("Content-Type: application/json")
    @POST("unenroll.php")
    Call<ApiResponse> unenroll(@Body CourseRequest request);


    @Headers("Content-Type: application/json")
    @POST("checkEnrollment.php")
    Call<ApiResponse> checkEnrollment(@Body CourseRequest request);



//    @FormUrlEncoded
//    @POST("get_user.php")
//    Call<UpdateProfileResponse> getUserProfile(
//            @Field("user_id") int userId
//    );

}
