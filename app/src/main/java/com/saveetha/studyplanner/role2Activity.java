package com.saveetha.studyplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

public class role2Activity extends AppCompatActivity {

    private Button btnLetsGo2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable edge-to-edge
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        setContentView(R.layout.activity_role2);

        // Initialize button
        btnLetsGo2 = findViewById(R.id.btnLetsGo2);

        // Set click listener
        btnLetsGo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open CourseDetailsPageActivity
                Intent intent = new Intent(role2Activity.this,coursedetailspageActivity.class);
                startActivity(intent);
            }
        });
    }
}
