package com.simats.studyplanner;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.simats.studyplanner.api.ApiClient;
import com.simats.studyplanner.api.ApiResponse;
import com.simats.studyplanner.api.ApiService;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditTaskActivity extends AppCompatActivity {

    EditText editTaskName, editTaskDescription, editTaskTime;
    TextView editTaskDate;
    Button btnHigh, btnMedium, btnLow;
    ImageView backArrow, submitButton, calendarIcon;

    String selectedPriority = "Medium";
    int taskId, userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edittaskpage); // Make sure this layout exists

        editTaskName = findViewById(R.id.edit_task_name);
        editTaskDescription = findViewById(R.id.edit_task_description);
        editTaskTime = findViewById(R.id.edit_task_time);
        editTaskDate = findViewById(R.id.edit_task_date);
        btnHigh = findViewById(R.id.edit_priority_high);
        btnMedium = findViewById(R.id.edit_priority_medium);
        btnLow = findViewById(R.id.edit_priority_low);
        backArrow = findViewById(R.id.back_arrow);
        submitButton = findViewById(R.id.submit_button);
        calendarIcon = findViewById(R.id.edit_task_calendar);

        // Receive task data from intent
        taskId = Integer.parseInt(Objects.requireNonNull(getIntent().getStringExtra("id")));
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        userId = sharedPreferences.getInt("user_id", -1);

        editTaskName.setText(getIntent().getStringExtra("task_name"));
        editTaskDescription.setText(getIntent().getStringExtra("description"));
        editTaskDate.setText(getIntent().getStringExtra("date"));
        editTaskTime.setText(getIntent().getStringExtra("time"));
        selectedPriority = getIntent().getStringExtra("priority");

        highlightPriority(selectedPriority);

        calendarIcon.setOnClickListener(v -> openDatePicker());
        editTaskTime.setOnClickListener(v -> openTimePicker());
        backArrow.setOnClickListener(v -> finish());

        btnHigh.setOnClickListener(v -> {
            selectedPriority = "High";
            highlightPriority("High");
        });
        btnMedium.setOnClickListener(v -> {
            selectedPriority = "Medium";
            highlightPriority("Medium");
        });
        btnLow.setOnClickListener(v -> {
            selectedPriority = "Low";
            highlightPriority("Low");
        });

        submitButton.setOnClickListener(v -> updateTask());
    }

    private void highlightPriority(String priority) {
        int redColor = getResources().getColor(android.R.color.holo_red_dark);
        int grayColor = getResources().getColor(android.R.color.darker_gray);

        btnHigh.setBackgroundColor(priority.equals("High") ? redColor : grayColor);
        btnMedium.setBackgroundColor(priority.equals("Medium") ? redColor : grayColor);
        btnLow.setBackgroundColor(priority.equals("Low") ? redColor : grayColor);

        btnHigh.setTextColor(priority.equals("High") ? getResources().getColor(android.R.color.white) : getResources().getColor(android.R.color.black));
        btnMedium.setTextColor(priority.equals("Medium") ? getResources().getColor(android.R.color.white) : getResources().getColor(android.R.color.black));
        btnLow.setTextColor(priority.equals("Low") ? getResources().getColor(android.R.color.white) : getResources().getColor(android.R.color.black));
    }

    private void openDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    String date = year + "-" + (month + 1) + "-" + dayOfMonth;
                    editTaskDate.setText(date);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void openTimePicker() {
        final Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (view, hourOfDay, minute) -> {
                    String time = String.format("%02d:%02d", hourOfDay, minute);
                    editTaskTime.setText(time);
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
        );
        timePickerDialog.show();
    }

    private void updateTask() {
        String name = editTaskName.getText().toString().trim();
        String description = editTaskDescription.getText().toString().trim();
        String date = editTaskDate.getText().toString().trim();
        String time = editTaskTime.getText().toString().trim();

        if (name.isEmpty() || description.isEmpty() || date.isEmpty() || time.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("task_id", String.valueOf(taskId));
        map.put("user_id", String.valueOf(userId));
        map.put("task", name);
        map.put("description", description);
        map.put("date", date);
        map.put("time", time);
        map.put("priority", selectedPriority);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<ApiResponse> call = apiService.updateTask(map);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(EditTaskActivity.this, "Task updated", Toast.LENGTH_SHORT).show();

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("id", taskId + "");
                    resultIntent.putExtra("task_name", name);
                    resultIntent.putExtra("description", description);
                    resultIntent.putExtra("date", date);
                    resultIntent.putExtra("time", time);
                    resultIntent.putExtra("priority", selectedPriority);
                    setResult(RESULT_OK, resultIntent);

                    finish();
                } else {
                    Toast.makeText(EditTaskActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(EditTaskActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
