package com.saveetha.studyplanner;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class addstudymaterialActivity extends AppCompatActivity {
    Button buttonsetreminder;
    ImageView backIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_addstudymaterial);

        buttonsetreminder = findViewById(R.id.buttonsetreminder);

        buttonsetreminder.setOnClickListener(v -> {
            finish(); });

        backIcon = findViewById(R.id.backstudy);

        backIcon.setOnClickListener(v -> {
            finish(); });

    }
}
