package com.simats.studyplanner;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;

import com.google.android.material.button.MaterialButton;

public class viewstudymaterialdetailspage6Activity extends AppCompatActivity {

    MaterialButton btnNext6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_viewstudymaterialdetailspage6);

        // Initialize the button
        btnNext6 = findViewById(R.id.btnNext6);

        // Set click listener
        btnNext6.setOnClickListener(view -> {
            Intent intent = new Intent(viewstudymaterialdetailspage6Activity.this, ViewStudyMaterialActivity.class);
            startActivity(intent);
        });
    }
}
