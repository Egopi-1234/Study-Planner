package com.simats.studyplanner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.simats.studyplanner.api.ApiClient;
import com.simats.studyplanner.api.ApiService;
import com.simats.studyplanner.api.ProfileResponse;
import com.simats.studyplanner.api.UpdateProfileResponse;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditprofiledetailspageActivity extends AppCompatActivity {

    EditText edit_name, edit_email, edit_phone, edit_dept;
    ImageView profileImage;
    Button btn_save, btn_cancel;

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri selectedImageUri;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofiledetailspage);

        edit_name = findViewById(R.id.edit_name);
        edit_email = findViewById(R.id.edit_email);
        edit_phone = findViewById(R.id.edit_phone);
        edit_dept = findViewById(R.id.edit_dept);
        profileImage = findViewById(R.id.profile_image);
        btn_save = findViewById(R.id.btn_save);
        btn_cancel = findViewById(R.id.btn_cancel);

        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        userId = sharedPreferences.getInt("user_id", -1);

        fetchUserProfile(String.valueOf(userId));
        profileImage.setOnClickListener(v -> openImageChooser());
        btn_save.setOnClickListener(v -> updateProfile());
        btn_cancel.setOnClickListener(v -> finish());
    }

    private void openImageChooser() {
        Intent intent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        } else {
            intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
        }
        startActivityForResult(Intent.createChooser(intent, "Select Profile Image"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            profileImage.setImageURI(selectedImageUri);
            // Add this line to show Toast
            Toast.makeText(this, "Image selected successfully", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchUserProfile(String userId) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        RequestBody userIdBody = RequestBody.create(MediaType.parse("text/plain"), userId);

        Call<ProfileResponse> call = apiService.viewProfile(userIdBody);
        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().status) {
                    ProfileResponse.UserData user = response.body().data.get(0);
                    edit_name.setText(user.name);
                    edit_email.setText(user.email);
                    edit_phone.setText(user.phone);
                    edit_dept.setText(user.Dept_info);

                    String fullImageUrl = "http://localhost/study_planner/" + user.profile_image;

                    Glide.with(EditprofiledetailspageActivity.this)
                            .load(fullImageUrl)
                            .placeholder(R.drawable.profile)
                            .into(profileImage);
                } else {
                    Toast.makeText(EditprofiledetailspageActivity.this, "Failed to load profile", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                Toast.makeText(EditprofiledetailspageActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateProfile() {
        String name = edit_name.getText().toString().trim();
        String email = edit_email.getText().toString().trim();
        String phone = edit_phone.getText().toString().trim();
        String deptInfo = edit_dept.getText().toString().trim();

        RequestBody userIdBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(userId));
        RequestBody nameBody = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody emailBody = RequestBody.create(MediaType.parse("text/plain"), email);
        RequestBody phoneBody = RequestBody.create(MediaType.parse("text/plain"), phone);
        RequestBody deptBody = RequestBody.create(MediaType.parse("text/plain"), deptInfo);

        MultipartBody.Part imagePart;
        if (selectedImageUri != null) {
            File imageFile = new File(getRealPathFromURI(selectedImageUri));
            RequestBody imageBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
            imagePart = MultipartBody.Part.createFormData("profile_image", imageFile.getName(), imageBody);
        } else {
            RequestBody empty = RequestBody.create(MediaType.parse("text/plain"), "");
            imagePart = MultipartBody.Part.createFormData("profile_image", "", empty);
        }

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<UpdateProfileResponse> call = apiService.updateProfile(userIdBody, nameBody, emailBody, phoneBody, deptBody, imagePart);

        call.enqueue(new Callback<UpdateProfileResponse>() {
            @Override
            public void onResponse(Call<UpdateProfileResponse> call, Response<UpdateProfileResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isStatus()) {
                    Toast.makeText(EditprofiledetailspageActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    try {
                        String str = response.errorBody().string();
                        Toast.makeText(EditprofiledetailspageActivity.this, str, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<UpdateProfileResponse> call, Throwable t) {
                Toast.makeText(EditprofiledetailspageActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String filePath = cursor.getString(column_index);
        cursor.close();
        return filePath;
    }
}
