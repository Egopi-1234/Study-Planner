package com.saveetha.studyplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;

public class role3Activity extends AppCompatActivity {

    private Button btnLetsGo3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_role3);

        // Initialize the button
        btnLetsGo3 = findViewById(R.id.btnLetsGo3);

        // Set click listener
        btnLetsGo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start CourseDetailsPageActivity
                Intent intent = new Intent(role3Activity.this, coursedetailspageActivity.class);
                startActivity(intent);
            }
        });
    }
}
