package com.saveetha.studyplanner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class TaskdetailsviewpageActivity extends AppCompatActivity {

    TextView taskName, taskDateTime, taskDescription, taskPriority, edit_task;
    CheckBox taskCompletedCheckbox;
    LinearLayout deleteTaskButton;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_taskdetailsviewpage);

        // Bind views
        taskName = findViewById(R.id.task_name_detail);
        taskDateTime = findViewById(R.id.task_datetime_detail);
        taskDescription = findViewById(R.id.task_description_value);
        taskPriority = findViewById(R.id.task_priority_detail);
        edit_task = findViewById(R.id.edit_task);
        taskCompletedCheckbox = findViewById(R.id.task_completed_checkbox);
        deleteTaskButton = findViewById(R.id.delete_task_button);
        backButton = findViewById(R.id.back_button);

        // Get data from Intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("task_name");
        String description = intent.getStringExtra("description");
        String date = intent.getStringExtra("date");
        String time = intent.getStringExtra("time");
        String priority = intent.getStringExtra("priority");
        boolean isCompleted = intent.getBooleanExtra("completed", false); // optional, if you pass it

        // Set values
        taskName.setText(name);
        taskDateTime.setText(date + " • " + time);
        taskDescription.setText(description);
        taskPriority.setText(priority);
        taskCompletedCheckbox.setChecked(isCompleted);

        // Edit button click - open edit screen
        edit_task.setOnClickListener(v -> {
            Intent editIntent = new Intent(TaskdetailsviewpageActivity.this, edittaskpageActivity.class);
            // Pass task details if needed
            editIntent.putExtra("task_name", name);
            editIntent.putExtra("description", description);
            editIntent.putExtra("date", date);
            editIntent.putExtra("time", time);
            editIntent.putExtra("priority", priority);
            startActivity(editIntent);
        });

        // Delete task button click - for now show a Toast (you can implement actual delete logic)
        deleteTaskButton.setOnClickListener(v -> {
            Toast.makeText(TaskdetailsviewpageActivity.this, "Delete task clicked", Toast.LENGTH_SHORT).show();
            // TODO: Implement delete functionality
        });

        // Back button click
        backButton.setOnClickListener(v -> finish());
    }
}
