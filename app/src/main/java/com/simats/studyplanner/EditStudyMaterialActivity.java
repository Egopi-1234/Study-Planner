package com.simats.studyplanner;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.simats.studyplanner.api.ApiClient;
import com.simats.studyplanner.api.ApiService;
import com.simats.studyplanner.api.EditMaterialResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditStudyMaterialActivity extends AppCompatActivity {

    private static final int PICK_FILE_REQUEST = 1;
    private static final int STORAGE_PERMISSION_CODE = 1001;

    private EditText editMaterialName, editSubject;
    private TextView editTime, textDueDate;
    private ImageView iconCalendar, doneBtn, backBtn;
    private Uri fileUri;

    private int materialId;
    private String materialName, subject, dueDate, dueTime, filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editstudymaterial);

        editMaterialName = findViewById(R.id.editTextMaterialName);
        editSubject = findViewById(R.id.editTextSubject);
        editTime = findViewById(R.id.editTextDueTime);  // TextView, not EditText
        textDueDate = findViewById(R.id.textViewDueDate);
        iconCalendar = findViewById(R.id.iconCalendar);
        doneBtn = findViewById(R.id.donearr);
        backBtn = findViewById(R.id.backstudy);

        backBtn.setOnClickListener(v -> finish());

        materialId = getIntent().getIntExtra("id", -1);
        materialName = getIntent().getStringExtra("name");
        subject = getIntent().getStringExtra("subject");
        dueDate = getIntent().getStringExtra("due_date");
        dueTime = getIntent().getStringExtra("due_time");
        filePath = getIntent().getStringExtra("file_path");

        editMaterialName.setText(materialName);
        editSubject.setText(subject);
        textDueDate.setText(dueDate);
        editTime.setText(dueTime);

        iconCalendar.setOnClickListener(v -> openDatePicker());
        editTime.setOnClickListener(v -> openTimePicker());

        findViewById(R.id.chooseFileBtn).setOnClickListener(v -> {
            if (checkStoragePermission()) {
                openFilePicker();
            }
        });

        doneBtn.setOnClickListener(v -> updateMaterialToServer());

        checkStoragePermission();
    }

    private boolean checkStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{
                                Manifest.permission.READ_MEDIA_IMAGES,
                                Manifest.permission.READ_MEDIA_VIDEO,
                                Manifest.permission.READ_MEDIA_AUDIO},
                        STORAGE_PERMISSION_CODE);
                return false;
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        STORAGE_PERMISSION_CODE);
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Storage permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Storage permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    String formattedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
                    textDueDate.setText(formattedDate);
                },
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void openTimePicker() {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        new TimePickerDialog(this,
                (view, hourOfDay, min) -> {
                    String formattedTime = String.format("%02d:%02d", hourOfDay, min);
                    editTime.setText(formattedTime);
                },
                hour, minute, true).show();
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{
                "application/pdf",
                "application/msword",
                "application/vnd.ms-powerpoint",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                "application/vnd.openxmlformats-officedocument.presentationml.presentation",
                "image/*"
        });
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Select File"), PICK_FILE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK && data != null) {
            fileUri = data.getData();
            Toast.makeText(this, "File selected: " + getFileName(fileUri), Toast.LENGTH_SHORT).show();
        }
    }

    private void updateMaterialToServer() {
        String name = editMaterialName.getText().toString().trim();
        String subject = editSubject.getText().toString().trim();
        String date = textDueDate.getText().toString().trim();
        String time = editTime.getText().toString().trim();

        if (name.isEmpty() || subject.isEmpty() || date.isEmpty() || time.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (fileUri == null) {
            Toast.makeText(this, "Please select a file to upload", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            InputStream inputStream = getContentResolver().openInputStream(fileUri);
            byte[] bytes = getBytes(inputStream);

            RequestBody materialIdBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(materialId));
            RequestBody userIdBody = RequestBody.create(MediaType.parse("text/plain"), "3"); // Replace with real user ID
            RequestBody nameBody = RequestBody.create(MediaType.parse("text/plain"), name);
            RequestBody subjectBody = RequestBody.create(MediaType.parse("text/plain"), subject);
            RequestBody dateBody = RequestBody.create(MediaType.parse("text/plain"), date);
            RequestBody timeBody = RequestBody.create(MediaType.parse("text/plain"), time);

            String mimeType = getContentResolver().getType(fileUri);
            String fileName = getFileName(fileUri);

            RequestBody fileBody = RequestBody.create(MediaType.parse(mimeType), bytes);
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", fileName, fileBody);

            ApiService apiService = ApiClient.getClient().create(ApiService.class);
            Call<EditMaterialResponse> call = apiService.updateMaterial(materialIdBody, userIdBody, nameBody, subjectBody, dateBody, timeBody, filePart);

            call.enqueue(new Callback<EditMaterialResponse>() {
                @Override
                public void onResponse(Call<EditMaterialResponse> call, Response<EditMaterialResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(EditStudyMaterialActivity.this, response.body().message, Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(EditStudyMaterialActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                        Log.e("API", "Response error: " + response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<EditMaterialResponse> call, Throwable t) {
                    Toast.makeText(EditStudyMaterialActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("API", "Failure", t);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to read file", Toast.LENGTH_SHORT).show();
        }
    }

    // Helper: read InputStream fully to byte array
    private byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    // Helper: get file name from Uri
    private String getFileName(Uri uri) {
        String result = null;
        if ("content".equals(uri.getScheme())) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (nameIndex >= 0) {
                        result = cursor.getString(nameIndex);
                    }
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }
}
