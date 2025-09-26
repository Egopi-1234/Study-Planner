package com.simats.studyplanner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.simats.studyplanner.api.Answer;
import com.simats.studyplanner.api.ApiClient;
import com.simats.studyplanner.api.ApiService;
import com.simats.studyplanner.api.SubmitTestRequest;
import com.simats.studyplanner.api.SubmitTestResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class mcqtestActivity extends AppCompatActivity {

    Button btnSubmit;
    RadioGroup rgQuestion1, rgQuestion2, rgQuestion3, rgQuestion4, rgQuestion5,
            rgQuestion6, rgQuestion7, rgQuestion8, rgQuestion9, rgQuestion10;

    private int userId;          // user_id from Intent extra or SharedPreferences fallback
    private String courseName;    // course name for this test

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mcqtest);

        // Toolbar back arrow
        MaterialToolbar toolbar = findViewById(R.id.bacck);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> {
            startActivity(new Intent(mcqtestActivity.this, ViewStudyMaterialActivity.class));
            finish();
        });

        // First try to get userId passed from previous activity/fragment via Intent extras
        Intent incoming = getIntent();
        if (incoming != null && incoming.hasExtra("user_id")) {
            userId = incoming.getIntExtra("user_id", -1);
        } else {
            userId = -1;
        }

        // If no intent extra, fallback to SharedPreferences (default 3)
        if (userId == -1) {
            SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
            userId = prefs.getInt("user_id", 3); // default ID = 3
        }

        // Get course name from Intent, default to "Python"
        courseName = incoming != null ? incoming.getStringExtra("course_name") : null;
        if (courseName == null || courseName.isEmpty()) {
            courseName = "Python";
        }

        // Initialize UI elements
        btnSubmit = findViewById(R.id.btnSubmit);
        rgQuestion1 = findViewById(R.id.q1);
        rgQuestion2 = findViewById(R.id.q2);
        rgQuestion3 = findViewById(R.id.q3);
        rgQuestion4 = findViewById(R.id.q4);
        rgQuestion5 = findViewById(R.id.q5);
        rgQuestion6 = findViewById(R.id.q6);
        rgQuestion7 = findViewById(R.id.q7);
        rgQuestion8 = findViewById(R.id.q8);
        rgQuestion9 = findViewById(R.id.q9);
        rgQuestion10 = findViewById(R.id.q10);

        btnSubmit.setOnClickListener(v -> submitTest());
    }

    private void submitTest() {
        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer(1, getSelectedOption(rgQuestion1)));
        answers.add(new Answer(2, getSelectedOption(rgQuestion2)));
        answers.add(new Answer(3, getSelectedOption(rgQuestion3)));
        answers.add(new Answer(4, getSelectedOption(rgQuestion4)));
        answers.add(new Answer(5, getSelectedOption(rgQuestion5)));
        answers.add(new Answer(6, getSelectedOption(rgQuestion6)));
        answers.add(new Answer(7, getSelectedOption(rgQuestion7)));
        answers.add(new Answer(8, getSelectedOption(rgQuestion8)));
        answers.add(new Answer(9, getSelectedOption(rgQuestion9)));
        answers.add(new Answer(10, getSelectedOption(rgQuestion10)));

        // Check all questions answered
        for (Answer a : answers) {
            if (a.getSelected_option() == null || a.getSelected_option().isEmpty()) {
                Toast.makeText(this, "Please answer all questions", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // Create request with userId + courseName + answers
        SubmitTestRequest request = new SubmitTestRequest(userId, courseName, answers);

        ApiService.MCQApi api = ApiClient.getClient().create(ApiService.MCQApi.class);
        api.submitTest(request).enqueue(new Callback<SubmitTestResponse>() {
            @Override
            public void onResponse(Call<SubmitTestResponse> call, Response<SubmitTestResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SubmitTestResponse res = response.body();
                    if (res.isSuccess()) {
                        Toast.makeText(mcqtestActivity.this, "Test submitted successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(mcqtestActivity.this, mcq_resultActivity.class);
                        intent.putExtra("score", res.getScore());
                        intent.putExtra("total_questions", res.getTotalQuestions());
                        intent.putExtra("attempt_id", res.getAttemptId()); // returned from backend
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(mcqtestActivity.this, "Submission failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mcqtestActivity.this, "Server error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SubmitTestResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(mcqtestActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getSelectedOption(RadioGroup rg) {
        if (rg == null) return "";
        int selectedId = rg.getCheckedRadioButtonId();
        if (selectedId == -1) return "";
        RadioButton rb = findViewById(selectedId);
        if (rb == null || rb.getText() == null) return "";
        String text = rb.getText().toString().trim();
        if (text.isEmpty()) return "";
        // return first character, usually "A", "B", "C", "D"
        return text.substring(0, 1);
    }
}
