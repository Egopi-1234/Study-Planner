package com.simats.studyplanner;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;

import com.google.android.material.button.MaterialButton;

public class viewstudymaterialdetailpage4Activity extends AppCompatActivity {

    MaterialButton btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_viewstudymaterialdetailspage4);

        // Initialize the button
        btn3 = findViewById(R.id.btn3);

        // Set click listener
        btn3.setOnClickListener(view -> {
            Intent intent = new Intent(viewstudymaterialdetailpage4Activity.this, viewstudymaterialdetailspage5Activity.class);
            startActivity(intent);
        });
    }
}
