package com.saveetha.studyplanner;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HelppageActivity extends AppCompatActivity {

    ImageView bkarr4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_helppage);

        bkarr4 = findViewById(R.id.bkarr4);
        bkarr4.setOnClickListener(v -> {

            finish();
        });


    }
}