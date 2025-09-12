package com.saveetha.studyplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;

import com.google.android.material.button.MaterialButton;

public class ViewStudyMaterialDetailPage2Activity extends AppCompatActivity {

    MaterialButton btnNext;
    View module1, module2, module3, module4, module5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_viewstudymaterialdetailspage2);

        // Initialize button
        btnNext = findViewById(R.id.btnNext);


// On button click, simulate progress for second module
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewStudyMaterialDetailPage2Activity.this, ViewStudyMaterialDetailPage3Activity.class);
                startActivity(intent);
            }
        });

    }
}
