package com.saveetha.studyplanner;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.*;
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
    Button btnHigh, btnMedium, btnLow, btnSetReminder;
    ImageButton backButton;
    String selectedPriority = "low";
    int userId = 5; // You can replace this with dynamic value from login/session

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        // View Bindings
        backButton = findViewById(R.id.backarrbt1);
        btnSetReminder = findViewById(R.id.btnSetReminder);
        btnHigh = findViewById(R.id.btnHigh);
        btnMedium = findViewById(R.id.btnMedium);
        btnLow = findViewById(R.id.btnLow);
        editTextTaskName = findViewById(R.id.editTextTaskName);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextTime = findViewById(R.id.editTextTime);
        textViewDate = findViewById(R.id.textViewDate);

        // Priority buttons
        btnHigh.setOnClickListener(v -> selectPriority("high"));
        btnMedium.setOnClickListener(v -> selectPriority("medium"));
        btnLow.setOnClickListener(v -> selectPriority("low"));

        // Pickers
        textViewDate.setOnClickListener(v -> showDatePicker());
        editTextTime.setOnClickListener(v -> showTimePicker());

        // Back
        backButton.setOnClickListener(v -> finish());

        // Submit
        btnSetReminder.setOnClickListener(v -> submitTask());
    }

    private void selectPriority(String priority) {
        selectedPriority = priority;

        btnHigh.setBackgroundTintList(getColorStateList(R.color.blue));
        btnMedium.setBackgroundTintList(getColorStateList(R.color.green));
        btnLow.setBackgroundTintList(getColorStateList(R.color.brown));

        if (priority.equals("high")) btnHigh.setBackgroundTintList(getColorStateList(R.color.red));
        else if (priority.equals("medium")) btnMedium.setBackgroundTintList(getColorStateList(R.color.red));
        else btnLow.setBackgroundTintList(getColorStateList(R.color.red));
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(this, (view, year, month, day) -> {
            String date = String.format("%04d-%02d-%02d", year, month + 1, day);
            textViewDate.setText(date);
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showTimePicker() {
        Calendar calendar = Calendar.getInstance();
        new TimePickerDialog(this, (view, hour, minute) -> {
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

    private RequestBody toRequestBody(String value) {
        return RequestBody.create(value, MediaType.parse("multipart/form-data"));
    }
}
