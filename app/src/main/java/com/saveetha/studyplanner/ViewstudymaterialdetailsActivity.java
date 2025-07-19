package com.saveetha.studyplanner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class ViewstudymaterialdetailsActivity extends AppCompatActivity {

    TextView editstudybtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_viewstudymaterialdetails);

        // Initialize the TextView by ID from XML
        editstudybtn = findViewById(R.id.editstudybtn);  // Make sure the ID matches your XML

        // Set click listener on the initialized TextView
        editstudybtn.setOnClickListener(v -> {
            // Fix the Intent source class (this class) and destination class
            startActivity(new Intent(ViewstudymaterialdetailsActivity.this, EditstudymaterialActivity.class));
        });
    }
}
