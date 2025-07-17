package com.saveetha.studyplanner;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.saveetha.studyplanner.api.ApiClient;
import com.saveetha.studyplanner.api.ApiService;
import com.saveetha.studyplanner.api.LoginResponse;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangepasswordpageActivity extends AppCompatActivity {

    EditText currentPassword, newPassword, confirmNewPassword;
    Button saveButton, cancelButton;

    int userId;  // Will be set from SharedPreferences

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_changepasswordpage);

        currentPassword = findViewById(R.id.currentPassword);
        newPassword = findViewById(R.id.newPassword);
        confirmNewPassword = findViewById(R.id.confirmNewPassword);
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);

        // Get userId from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
        userId = prefs.getInt("user_id", -1);

        if (userId == -1) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            finish(); // Exit activity if not logged in
            return;
        }

        saveButton.setOnClickListener(v -> {
            String oldPass = currentPassword.getText().toString().trim();
            String newPass = newPassword.getText().toString().trim();
            String confirmPass = confirmNewPassword.getText().toString().trim();

            if (TextUtils.isEmpty(oldPass) || TextUtils.isEmpty(newPass) || TextUtils.isEmpty(confirmPass)) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!newPass.equals(confirmPass)) {
                Toast.makeText(this, "New Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            changePassword(userId, oldPass, newPass, confirmPass);
        });

        cancelButton.setOnClickListener(v -> finish());
    }

    private void changePassword(int userId, String oldPass, String newPass, String confirmPass) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        RequestBody userIdPart = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(userId));
        RequestBody oldPassPart = RequestBody.create(MediaType.parse("text/plain"), oldPass);
        RequestBody newPassPart = RequestBody.create(MediaType.parse("text/plain"), newPass);
        RequestBody confirmPassPart = RequestBody.create(MediaType.parse("text/plain"), confirmPass);

        Call<LoginResponse> call = apiService.changePassword(userIdPart, oldPassPart, newPassPart, confirmPassPart);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isStatus()) {
                        Toast.makeText(ChangepasswordpageActivity.this, "Password Changed Successfully", Toast.LENGTH_LONG).show();
                        finish(); // Close activity
                    } else {
                        Toast.makeText(ChangepasswordpageActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ChangepasswordpageActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(ChangepasswordpageActivity.this, "Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
