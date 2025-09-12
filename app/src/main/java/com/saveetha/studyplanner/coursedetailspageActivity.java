package com.saveetha.studyplanner;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class coursedetailspageActivity extends AppCompatActivity {

    Button btnEnroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_coursedetailspage);

        btnEnroll = findViewById(R.id.btnEnroll);

        updateEnrollButton();

        btnEnroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isEnrolled = getSharedPreferences("studyplanner", MODE_PRIVATE)
                        .getBoolean("isEnrolled", false);

                if (!isEnrolled) {
                    // Enroll user
                    getSharedPreferences("studyplanner", MODE_PRIVATE)
                            .edit()
                            .putBoolean("isEnrolled", true)
                            .apply();

                    Toast.makeText(coursedetailspageActivity.this,
                            "Successfully enrolled in the course!",
                            Toast.LENGTH_SHORT).show();

                    updateEnrollButton();

                    // Navigate to Homepage → Study Material fragment
                    Intent intent = new Intent(coursedetailspageActivity.this, HomepageActivity.class);
                    intent.putExtra("openFragment", "material");
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();

                } else {
                    // Already enrolled → confirm unenroll
                    new AlertDialog.Builder(coursedetailspageActivity.this)
                            .setTitle("Unenroll Confirmation")
                            .setMessage("Are you sure you want to unenroll from this course?")
                            .setPositiveButton("Yes", (dialog, which) -> {
                                // Unenroll user
                                getSharedPreferences("studyplanner", MODE_PRIVATE)
                                        .edit()
                                        .putBoolean("isEnrolled", false)
                                        .apply();

                                Toast.makeText(coursedetailspageActivity.this,
                                        "You have been unenrolled from the course.",
                                        Toast.LENGTH_SHORT).show();

                                updateEnrollButton();

                                // Navigate to Homepage → Home fragment
                                Intent intent = new Intent(coursedetailspageActivity.this, HomepageActivity.class);
                                intent.putExtra("openFragment", "home"); // specify home fragment
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();

                            })
                            .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                            .show();
                }
            }
        });
    }

    private void updateEnrollButton() {
        boolean isEnrolled = getSharedPreferences("studyplanner", MODE_PRIVATE)
                .getBoolean("isEnrolled", false);

        if (isEnrolled) {
            btnEnroll.setText("Enrolled");
            btnEnroll.setBackgroundColor(Color.parseColor("#4CAF50")); // Green
        } else {
            btnEnroll.setText("Enroll");
            btnEnroll.setBackgroundColor(Color.parseColor("#FF6200EE")); // Original color
        }
    }
}

