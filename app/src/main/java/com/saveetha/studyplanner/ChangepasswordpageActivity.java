package com.saveetha.studyplanner;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class ChangepasswordpageActivity extends AppCompatActivity {

    Button saveButton;
    Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_changepasswordpage);

        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);

        saveButton.setOnClickListener(v -> {
            // Save password logic here (if any), then go back
            finish();
        });

        cancelButton.setOnClickListener(v -> {
            // Just cancel and go back
            finish();
        });
    }
}
