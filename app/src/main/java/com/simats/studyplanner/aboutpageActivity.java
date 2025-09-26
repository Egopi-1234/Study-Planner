package com.simats.studyplanner;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class aboutpageActivity extends AppCompatActivity {

    ImageView bkarr2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_aboutpage);

        bkarr2 = findViewById(R.id.bkarr2);
        bkarr2.setOnClickListener(v -> {

            finish();
        });


    }
}