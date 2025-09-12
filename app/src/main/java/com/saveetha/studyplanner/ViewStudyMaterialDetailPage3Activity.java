package com.saveetha.studyplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;

import com.google.android.material.button.MaterialButton;

public class ViewStudyMaterialDetailPage3Activity extends AppCompatActivity {

  MaterialButton btnNext2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_viewstudymaterialdetailspage3);

        // Initialize button
        btnNext2 = findViewById(R.id.btnNext2);

        // Set click listener to go to page 3
        btnNext2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewStudyMaterialDetailPage3Activity.this, viewstudymaterialdetailpage4Activity.class);
                startActivity(intent);
            }
        });


    }
}
