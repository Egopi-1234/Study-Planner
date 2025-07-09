package com.saveetha.studyplanner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TaskdetailsviewpageActivity extends AppCompatActivity {

    TextView edit_task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_taskdetailsviewpage);

        edit_task = findViewById(R.id.edit_task);


        edit_task.setOnClickListener(v -> {
            startActivity(new Intent(TaskdetailsviewpageActivity.this, edittaskpageActivity.class));
        });
    }
}
