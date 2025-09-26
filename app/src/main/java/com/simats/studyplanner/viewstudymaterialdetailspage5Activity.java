package com.simats.studyplanner;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;

import com.google.android.material.button.MaterialButton;

public class viewstudymaterialdetailspage5Activity extends AppCompatActivity {

    MaterialButton btnNext5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_viewstudymaterialdetailspage5);

        // Initialize the button
        btnNext5 = findViewById(R.id.btnNext5);

        // Set click listener
        btnNext5.setOnClickListener(view -> {
            Intent intent = new Intent(viewstudymaterialdetailspage5Activity.this, viewstudymaterialdetailspage6Activity.class);
            startActivity(intent);
        });
    }
}
