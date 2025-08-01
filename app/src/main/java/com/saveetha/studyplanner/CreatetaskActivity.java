package com.saveetha.studyplanner;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.saveetha.studyplanner.api.ApiClient;
import com.saveetha.studyplanner.api.ApiService;
import com.saveetha.studyplanner.api.AddTaskResponse;

import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatetaskActivity extends AppCompatActivity {

    EditText editTextTaskName, editTextDescription, editTextTime;
    TextView textViewDate;
    ImageView iconCalendar;
    Button btnHigh, btnMedium, btnLow, btnSetReminder;
    ImageButton backButton;
    String selectedPriority = "low";
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        userId = sharedPreferences.getInt("user_id", -1);

        backButton = findViewById(R.id.backarrbt1);
        btnSetReminder = findViewById(R.id.btnSetReminder);
        btnHigh = findViewById(R.id.btnHigh);
        btnMedium = findViewById(R.id.btnMedium);
        btnLow = findViewById(R.id.btnLow);
        editTextTaskName = findViewById(R.id.editTextTaskName);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextTime = findViewById(R.id.editTextTime);
        textViewDate = findViewById(R.id.textViewDate);
        iconCalendar = findViewById(R.id.iconCalendar);

        btnHigh.setOnClickListener(v -> selectPriority("high"));
        btnMedium.setOnClickListener(v -> selectPriority("medium"));
        btnLow.setOnClickListener(v -> selectPriority("low"));

        textViewDate.setOnClickListener(v -> showDatePicker());
        iconCalendar.setOnClickListener(v -> showDatePicker());
        editTextTime.setOnClickListener(v -> showTimePicker());

        backButton.setOnClickListener(v -> finish());

        btnSetReminder.setOnClickListener(v -> submitTask());
    }

    private void selectPriority(String priority) {
        selectedPriority = priority;
        btnHigh.setBackgroundTintList(getColorStateList(R.color.blue));
        btnMedium.setBackgroundTintList(getColorStateList(R.color.green));
        btnLow.setBackgroundTintList(getColorStateList(R.color.brown));

        if (priority.equals("high")) {
            btnHigh.setBackgroundTintList(getColorStateList(R.color.red));
        } else if (priority.equals("medium")) {
            btnMedium.setBackgroundTintList(getColorStateList(R.color.red));
        } else {
            btnLow.setBackgroundTintList(getColorStateList(R.color.red));
        }
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        new android.app.DatePickerDialog(this, (view, year, month, day) -> {
            String date = String.format("%04d-%02d-%02d", year, month + 1, day);
            textViewDate.setText(date);
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showTimePicker() {
        Calendar calendar = Calendar.getInstance();
        new android.app.TimePickerDialog(this, (view, hour, minute) -> {
            String time = String.format("%02d:%02d:00", hour, minute);
            editTextTime.setText(time);
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
    }

    private void submitTask() {
        String task = editTextTaskName.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String date = textViewDate.getText().toString().trim();
        String time = editTextTime.getText().toString().trim();

        if (task.isEmpty() || description.isEmpty() || date.isEmpty() || time.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userId == -1) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<AddTaskResponse> call = apiService.addTask(
                toRequestBody(String.valueOf(userId)),
                toRequestBody(task),
                toRequestBody(description),
                toRequestBody(date),
                toRequestBody(time),
                toRequestBody(selectedPriority)
        );

        call.enqueue(new Callback<AddTaskResponse>() {
            @Override
            public void onResponse(Call<AddTaskResponse> call, Response<AddTaskResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().status) {
                    Toast.makeText(CreatetaskActivity.this, "Task added successfully!", Toast.LENGTH_SHORT).show();

                    // Schedule notification with both task name and description
                    scheduleTaskNotification(task, date, time, description);

                    finish();
                } else {
                    Toast.makeText(CreatetaskActivity.this, "Failed to add task", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddTaskResponse> call, Throwable t) {
                Toast.makeText(CreatetaskActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void scheduleTaskNotification(String taskName, String date, String time, String description) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!alarmManager.canScheduleExactAlarms()) {
                Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                startActivity(intent);
                Toast.makeText(this, "Please allow exact alarms permission for reminders.", Toast.LENGTH_LONG).show();
                return;
            }
        }

        Calendar calendar = Calendar.getInstance();
        try {
            String[] dateParts = date.split("-");
            String[] timeParts = time.split(":");
            calendar.set(Calendar.YEAR, Integer.parseInt(dateParts[0]));
            calendar.set(Calendar.MONTH, Integer.parseInt(dateParts[1]) - 1);
            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateParts[2]));
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeParts[0]));
            calendar.set(Calendar.MINUTE, Integer.parseInt(timeParts[1]));
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Invalid date/time format for notification", Toast.LENGTH_SHORT).show();
            return;
        }

        long triggerAtMillis = calendar.getTimeInMillis();
        long currentMillis = System.currentTimeMillis();

        if (triggerAtMillis <= currentMillis) {
            Toast.makeText(this, "Task time is in the past. Notification not scheduled.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, TaskNotificationReceiver.class);
        intent.putExtra("task_name", taskName);
        intent.putExtra("task_description", description);

        int requestCode = (taskName + triggerAtMillis).hashCode();

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                requestCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
    }

    private RequestBody toRequestBody(String value) {
        return RequestBody.create(value, MediaType.parse("multipart/form-data"));
    }
}
