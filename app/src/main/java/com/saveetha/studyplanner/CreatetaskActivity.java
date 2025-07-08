package com.saveetha.studyplanner;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class CreatetaskActivity extends AppCompatActivity {
    ImageButton backButton;
    Button btnSetReminder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_task);

        backButton = findViewById(R.id.backarrowbtn);

        backButton.setOnClickListener(v -> {
            finish();
        });
        btnSetReminder = findViewById(R.id.btnSetReminder);

        btnSetReminder.setOnClickListener(v -> {
            finish(); });

    }
}