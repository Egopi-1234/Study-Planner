package com.simats.studyplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

public class role1Activity extends AppCompatActivity {

    private Button btnLetsGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Edge-to-edge
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        setContentView(R.layout.activity_role1);

        // Initialize button
        btnLetsGo = findViewById(R.id.btnLetsGo);

        // Set click listener
        btnLetsGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to CourseDetailsPageActivity
                Intent intent = new Intent(role1Activity.this, coursedetailspageActivity.class);
                startActivity(intent);
            }
        });
    }
}
