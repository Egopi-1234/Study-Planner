package com.simats.studyplanner;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.simats.studyplanner.api.ApiClient;
import com.simats.studyplanner.api.ApiService;
import com.simats.studyplanner.api.UploadMaterialResponse;

import java.io.File;
import java.util.Calendar;

import okhttp3.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class addstudymaterialActivity extends AppCompatActivity {

    EditText editTextMaterialName, editTextSubject;
    TextView textViewDueDate,editTextDueTime;
    ImageView backIcon, iconCalendar;
    Button buttonsetreminder;

    Uri selectedFileUri;
    LinearLayout fileLayout;

    ApiService apiService;

    static final int FILE_SELECT_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addstudymaterial);

        editTextMaterialName = findViewById(R.id.editTextMaterialName);
        editTextSubject = findViewById(R.id.editTextSubject);
        editTextDueTime = findViewById(R.id.editTextDueTime);
        textViewDueDate = findViewById(R.id.textViewDueDate);
        backIcon = findViewById(R.id.backstudy);
        iconCalendar = findViewById(R.id.iconCalendar);
        buttonsetreminder = findViewById(R.id.buttonsetreminder);
        fileLayout = findViewById(R.id.fileChooseIcon); // Make sure this id exists in your XML wrapping your file picker views

        apiService = ApiClient.getClient().create(ApiService.class);

        backIcon.setOnClickListener(v -> finish());

        iconCalendar.setOnClickListener(v -> openDatePicker());

        editTextDueTime.setOnClickListener(v -> openTimePicker());

        fileLayout.setOnClickListener(v -> openFileChooser());

        buttonsetreminder.setOnClickListener(v -> uploadMaterial());
    }

    void openDatePicker() {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(this, (view, year, month, day) -> {
            textViewDueDate.setText(String.format("%04d-%02d-%02d", year, month + 1, day));
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    void openTimePicker() {
        Calendar calendar = Calendar.getInstance();
        new TimePickerDialog(this, (view, hour, minute) -> {
            // Formatting with seconds "HH:mm:ss"
            editTextDueTime.setText(String.format("%02d:%02d:00", hour, minute));
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
    }

    void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(Intent.createChooser(intent, "Select File"), FILE_SELECT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_SELECT_CODE && resultCode == RESULT_OK && data != null) {
            selectedFileUri = data.getData();
            String fileName = FileUtils.getFileName(this, selectedFileUri);
            Toast.makeText(this, "Selected: " + fileName, Toast.LENGTH_SHORT).show();
        }
    }

    void uploadMaterial() {
        String name = editTextMaterialName.getText().toString().trim();
        String subject = editTextSubject.getText().toString().trim();
        String dueDate = textViewDueDate.getText().toString().trim();
        String dueTime = editTextDueTime.getText().toString().trim();

        // Retrieve user ID dynamically from SharedPreferences
        int userId = getSharedPreferences("UserSession", MODE_PRIVATE).getInt("user_id", -1);
        if (userId == -1) {
            Toast.makeText(this, "User ID not found. Please login.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (name.isEmpty() || subject.isEmpty() || dueDate.isEmpty() || dueTime.isEmpty() || selectedFileUri == null) {
            Toast.makeText(this, "All fields and file are required", Toast.LENGTH_SHORT).show();
            return;
        }

        File file = FileUtils.getFile(this, selectedFileUri);

        RequestBody rbName = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody rbSubject = RequestBody.create(MediaType.parse("text/plain"), subject);
        RequestBody rbDueDate = RequestBody.create(MediaType.parse("text/plain"), dueDate);
        RequestBody rbDueTime = RequestBody.create(MediaType.parse("text/plain"), dueTime);
        RequestBody rbUserId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(userId));

        RequestBody fileReq = RequestBody.create(MediaType.parse(getContentResolver().getType(selectedFileUri)), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), fileReq);

        apiService.uploadMaterial(rbName, rbSubject, rbDueDate, rbDueTime, rbUserId, filePart)
                .enqueue(new Callback<UploadMaterialResponse>() {
                    @Override
                    public void onResponse(Call<UploadMaterialResponse> call, Response<UploadMaterialResponse> response) {
                        if (response.isSuccessful() && response.body() != null && response.body().status) {
                            Toast.makeText(addstudymaterialActivity.this, "Uploaded successfully!", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(addstudymaterialActivity.this, "Upload failed!", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UploadMaterialResponse> call, Throwable t) {
                        Toast.makeText(addstudymaterialActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
