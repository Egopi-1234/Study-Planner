package com.saveetha.studyplanner;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.saveetha.studyplanner.api.ApiClient;
import com.saveetha.studyplanner.api.ApiService;
import com.saveetha.studyplanner.api.DeleteTaskResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskdetailsviewpageActivity extends AppCompatActivity {

    TextView taskName, taskDescription, taskDate, taskTime, taskPriority, editTask;
    CheckBox taskCompletedCheckbox;
    LinearLayout deleteTaskButton;
    ImageView backButton;

    String id, name, description, date, time, priority;
    boolean isCompleted;

    ActivityResultLauncher<Intent> editTaskLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();
                    name = data.getStringExtra("task_name");
                    id = data.getStringExtra("id");
                    description = data.getStringExtra("description");
                    date = data.getStringExtra("date");
                    time = data.getStringExtra("time");
                    priority = data.getStringExtra("priority");

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

        // View bindings
        taskName = findViewById(R.id.task_name_detail);
        taskDescription = findViewById(R.id.task_description_value);
        taskDate = findViewById(R.id.task_date_value);
        taskTime = findViewById(R.id.task_time_value);
        taskPriority = findViewById(R.id.task_priority_detail);
        editTask = findViewById(R.id.edit_task);
        taskCompletedCheckbox = findViewById(R.id.task_completed_checkbox);
        deleteTaskButton = findViewById(R.id.delete_task_button);
        backButton = findViewById(R.id.back_button);

        // Receive task data
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        name = intent.getStringExtra("task_name");
        description = intent.getStringExtra("description");
        date = intent.getStringExtra("date");
        time = intent.getStringExtra("time");
        priority = intent.getStringExtra("priority");
        isCompleted = intent.getBooleanExtra("completed", false);

        // Set data to views
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

        // Edit task
        editTask.setOnClickListener(v -> {
            Intent editIntent = new Intent(TaskdetailsviewpageActivity.this, EditTaskActivity.class);
            editIntent.putExtra("id", id);
            editIntent.putExtra("task_name", name);
            editIntent.putExtra("description", description);
            editIntent.putExtra("date", date);
            editIntent.putExtra("time", time);
            editIntent.putExtra("priority", priority);
            editTaskLauncher.launch(editIntent);
        });

        // Handle checkbox status change
        taskCompletedCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String newStatus = isChecked ? "Complete" : "Pending";

            SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
            int userId = prefs.getInt("user_id", -1);

            if (userId == -1) {
                Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show();
                return;
            }

            RequestBody userIdPart = RequestBody.create(MultipartBody.FORM, String.valueOf(userId));
            RequestBody taskIdPart = RequestBody.create(MultipartBody.FORM, id);
            RequestBody statusPart = RequestBody.create(MultipartBody.FORM, newStatus);

            ApiService apiService = ApiClient.getClient().create(ApiService.class);
            Call<DeleteTaskResponse> call = apiService.updateTaskStatus(userIdPart, taskIdPart, statusPart);

            call.enqueue(new Callback<DeleteTaskResponse>() {
                @Override
                public void onResponse(Call<DeleteTaskResponse> call, Response<DeleteTaskResponse> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().status) {
                        Toast.makeText(TaskdetailsviewpageActivity.this, "Task status updated to " + newStatus, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(TaskdetailsviewpageActivity.this, "Failed to update status", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<DeleteTaskResponse> call, Throwable t) {
                    Toast.makeText(TaskdetailsviewpageActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        // Delete task
        deleteTaskButton.setOnClickListener(v -> {
            SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
            int userId = prefs.getInt("user_id", -1);

            if (userId == -1) {
                Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show();
                return;
            }

            RequestBody userIdPart = RequestBody.create(MultipartBody.FORM, String.valueOf(userId));
            RequestBody taskIdPart = RequestBody.create(MultipartBody.FORM, id);

            ApiService apiService = ApiClient.getClient().create(ApiService.class);
            Call<DeleteTaskResponse> call = apiService.deleteTask(userIdPart, taskIdPart);

            call.enqueue(new Callback<DeleteTaskResponse>() {
                @Override
                public void onResponse(Call<DeleteTaskResponse> call, Response<DeleteTaskResponse> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().status) {
                        Toast.makeText(TaskdetailsviewpageActivity.this, "Task deleted successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(TaskdetailsviewpageActivity.this, "Failed to delete task", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<DeleteTaskResponse> call, Throwable t) {
                    Toast.makeText(TaskdetailsviewpageActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        // Back button
        backButton.setOnClickListener(v -> finish());
    }
}
