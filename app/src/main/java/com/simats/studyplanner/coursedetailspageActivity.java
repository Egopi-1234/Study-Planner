package com.simats.studyplanner;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.simats.studyplanner.api.ApiClient;
import com.simats.studyplanner.api.ApiResponse;
import com.simats.studyplanner.api.ApiService;
import com.simats.studyplanner.api.CourseRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class coursedetailspageActivity extends AppCompatActivity {

    Button btnEnroll;
    ApiService apiService;
    String courseName = "python"; // Replace dynamically if needed
    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_coursedetailspage);

        btnEnroll = findViewById(R.id.btnEnroll);
        apiService = ApiClient.getClient().create(ApiService.class);

        // Get logged-in user's email from SharedPreferences
        userEmail = getSharedPreferences("UserSession", MODE_PRIVATE)
                .getString("email", "");

        // 1️⃣ Check enrollment status immediately
        checkEnrollmentStatus();

        btnEnroll.setOnClickListener(v -> {
            boolean isEnrolled = getSharedPreferences("studyplanner", MODE_PRIVATE)
                    .getBoolean("isEnrolled", false);

            if (!isEnrolled) {
                // 2️⃣ Instant UI feedback
                btnEnroll.setText("Enrolled");
                btnEnroll.setBackgroundColor(Color.parseColor("#4CAF50")); // Green
                btnEnroll.setEnabled(false);

                Toast.makeText(this, "Successfully enrolled in the course!", Toast.LENGTH_SHORT).show();

                // 3️⃣ Navigate immediately to Study Material
                navigateToStudyMaterial();

                // 4️⃣ Store enrollment in backend
                enrollUser();

            } else {
                // Already enrolled → double-tap to confirm unenroll
                new AlertDialog.Builder(this)
                        .setTitle("Unenroll Confirmation")
                        .setMessage("Are you sure you want to unenroll from this course?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            btnEnroll.setText("Enroll Now");
                            btnEnroll.setBackgroundColor(Color.parseColor("#3665AB")); // Blue
                            btnEnroll.setEnabled(false);

                            unenrollUser();
                        })
                        .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                        .show();
            }
        });
    }

    // Check backend for enrollment status
    private void checkEnrollmentStatus() {
        CourseRequest request = new CourseRequest(courseName, userEmail);
        apiService.checkEnrollment(request).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    boolean enrolled = response.body().status;

                    // Save locally
                    getSharedPreferences("studyplanner", MODE_PRIVATE)
                            .edit()
                            .putBoolean("isEnrolled", enrolled)
                            .apply();

                    // Update button UI
                    if (enrolled) {
                        btnEnroll.setText("Enrolled");
                        btnEnroll.setBackgroundColor(Color.parseColor("#4CAF50")); // Green
                    } else {
                        btnEnroll.setText("Enroll Now");
                        btnEnroll.setBackgroundColor(Color.parseColor("#3665AB")); // Blue
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(coursedetailspageActivity.this,
                        "Error checking enrollment: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void enrollUser() {
        CourseRequest request = new CourseRequest(courseName, userEmail);
        apiService.enroll(request).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                btnEnroll.setEnabled(true);
                if (response.isSuccessful() && response.body() != null && response.body().status) {
                    // Save locally
                    getSharedPreferences("studyplanner", MODE_PRIVATE)
                            .edit()
                            .putBoolean("isEnrolled", true)
                            .apply();
                } else {
                    // Revert button if backend fails
                    btnEnroll.setText("Enroll Now");
                    btnEnroll.setBackgroundColor(Color.parseColor("#3665AB"));
                    Toast.makeText(coursedetailspageActivity.this,
                            response.body() != null ? response.body().message : "Enrollment failed!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                btnEnroll.setEnabled(true);
                btnEnroll.setText("Enroll Now");
                btnEnroll.setBackgroundColor(Color.parseColor("#3665AB"));
                Toast.makeText(coursedetailspageActivity.this,
                        "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void unenrollUser() {
        CourseRequest request = new CourseRequest(courseName, userEmail);
        apiService.unenroll(request).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                btnEnroll.setEnabled(true);
                if (response.isSuccessful() && response.body() != null && response.body().status) {
                    getSharedPreferences("studyplanner", MODE_PRIVATE)
                            .edit()
                            .putBoolean("isEnrolled", false)
                            .apply();
                } else {
                    btnEnroll.setText("Enrolled");
                    btnEnroll.setBackgroundColor(Color.parseColor("#4CAF50"));
                    Toast.makeText(coursedetailspageActivity.this,
                            response.body() != null ? response.body().message : "Unenroll failed!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                btnEnroll.setEnabled(true);
                btnEnroll.setText("Enrolled");
                btnEnroll.setBackgroundColor(Color.parseColor("#4CAF50"));
                Toast.makeText(coursedetailspageActivity.this,
                        "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToStudyMaterial() {
        Intent intent = new Intent(coursedetailspageActivity.this, HomepageActivity.class);
        intent.putExtra("openFragment", "material");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
