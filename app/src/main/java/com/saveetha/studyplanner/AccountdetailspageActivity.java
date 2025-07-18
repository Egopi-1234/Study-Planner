package com.saveetha.studyplanner;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.saveetha.studyplanner.api.ApiClient;
import com.saveetha.studyplanner.api.ApiService;
import com.saveetha.studyplanner.api.ProfileResponse;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountdetailspageActivity extends AppCompatActivity {

    ImageView bkarr1, profileImageView;
    TextView nameEditText, emailEditText, phoneEditText, deptEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountdetailspage);

        // Bind Views
        bkarr1 = findViewById(R.id.bkarr1);
        profileImageView = findViewById(R.id.profileImageView);
        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        deptEditText = findViewById(R.id.deptEditText);

        // Handle back button click
        bkarr1.setOnClickListener(v -> finish());

        // Fetch and display profile data
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id",-1);
        fetchUserProfile(String.valueOf(userId)); // Pass user ID dynamically if needed
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

                    nameEditText.setText(user.name);
                    emailEditText.setText(user.email);
                    phoneEditText.setText(user.phone);
                    deptEditText.setText(user.Dept_info);

                    String fullImageUrl = "http://localhost/study_planner/" + user.profile_image;

                    Glide.with(AccountdetailspageActivity.this)
                            .load(fullImageUrl)
                            .placeholder(R.drawable.profile)
                            .into(profileImageView);
                } else {
                    Toast.makeText(AccountdetailspageActivity.this, "Failed to load profile", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                Toast.makeText(AccountdetailspageActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
