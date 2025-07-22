package com.saveetha.studyplanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class TaskdetailsviewpageActivity extends AppCompatActivity {

    TextView taskName, taskDescription, taskDate, taskTime, taskPriority, editTask;
    CheckBox taskCompletedCheckbox;
    LinearLayout deleteTaskButton;
    ImageView backButton;

    String id,name, description, date, time, priority;
    boolean isCompleted;

    // ✅ ActivityResultLauncher for editing task
    ActivityResultLauncher<Intent> editTaskLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();

                    // Get updated data from EditTaskActivity
                    name = data.getStringExtra("task_name");
                    id = data.getStringExtra("id");
                    description = data.getStringExtra("description");
                    date = data.getStringExtra("date");
                    time = data.getStringExtra("time");
                    priority = data.getStringExtra("priority");

                    // Update UI
                    taskName.setText(name);
                    taskDescription.setText(description != null ? description : "");
                    taskDate.setText(date);
                    taskTime.setText(time);
                    taskPriority.setText(priority != null ? priority : "None");
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_taskdetailsviewpage);

        taskName = findViewById(R.id.task_name_detail);
        taskDescription = findViewById(R.id.task_description_value);
        taskDate = findViewById(R.id.task_date_value);
        taskTime = findViewById(R.id.task_time_value);
        taskPriority = findViewById(R.id.task_priority_detail);
        editTask = findViewById(R.id.edit_task);
        taskCompletedCheckbox = findViewById(R.id.task_completed_checkbox);
        deleteTaskButton = findViewById(R.id.delete_task_button);
        backButton = findViewById(R.id.back_button);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        name = intent.getStringExtra("task_name");
        description = intent.getStringExtra("description");
        date = intent.getStringExtra("date");
        time = intent.getStringExtra("time");
        priority = intent.getStringExtra("priority");
        isCompleted = intent.getBooleanExtra("completed", false);

        if (name != null && date != null && time != null) {
            taskName.setText(name);
            taskDescription.setText(description != null ? description : "");
            taskDate.setText(date);
            taskTime.setText(time);
            taskPriority.setText(priority != null ? priority : "None");
            taskCompletedCheckbox.setChecked(isCompleted);
        } else {
            Toast.makeText(this, "Task details are missing!", Toast.LENGTH_SHORT).show();
            finish();
        }

        editTask.setOnClickListener(v -> {
            Intent editIntent = new Intent(TaskdetailsviewpageActivity.this, EditTaskActivity.class);
            editIntent.putExtra("id",id+"");
            editIntent.putExtra("task_name", name);
            editIntent.putExtra("description", description);
            editIntent.putExtra("date", date);
            editIntent.putExtra("time", time);
            editIntent.putExtra("priority", priority);
            // You can also pass "task_id" and "user_id" if needed for update
            editTaskLauncher.launch(editIntent);
        });

        taskCompletedCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(this, "Task marked as completed!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Task marked as not completed", Toast.LENGTH_SHORT).show();
            }
        });

        deleteTaskButton.setOnClickListener(v -> {
            Toast.makeText(TaskdetailsviewpageActivity.this, "Delete task clicked", Toast.LENGTH_SHORT).show();
        });

        backButton.setOnClickListener(v -> finish());
    }
}
