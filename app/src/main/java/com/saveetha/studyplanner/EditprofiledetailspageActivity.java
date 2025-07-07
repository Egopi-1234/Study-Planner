package com.saveetha.studyplanner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EditprofiledetailspageActivity extends AppCompatActivity {
    Button btn_save;
    Button btn_cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editprofiledetailspage);

        btn_save = findViewById(R.id.btn_save);

        btn_save.setOnClickListener(v -> {
           finish();});
        btn_cancel = findViewById(R.id.btn_cancel);

        btn_cancel.setOnClickListener(v -> {
           finish(); });

    }
}