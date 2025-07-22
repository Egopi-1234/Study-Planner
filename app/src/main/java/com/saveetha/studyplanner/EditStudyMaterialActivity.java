package com.saveetha.studyplanner;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class EditStudyMaterialActivity extends AppCompatActivity {

    private static final int PICK_PDF_REQUEST = 1;

    private EditText editMaterialName, editSubject, editTime;
    private TextView textDueDate;
    private ImageView iconCalendar, doneBtn, backBtn;
    private Uri fileUri;

    // Data from Intent
    private int materialId;
    private String materialName, subject, dueDate, dueTime, filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editstudymaterial);

        // Bind UI elements
        editMaterialName = findViewById(R.id.editTextMaterialName);
        editSubject = findViewById(R.id.editTextSubject);
        editTime = findViewById(R.id.editTextDueTime);
        textDueDate = findViewById(R.id.textViewDueDate);
        iconCalendar = findViewById(R.id.iconCalendar);
        doneBtn = findViewById(R.id.donearr);
        backBtn = findViewById(R.id.backstudy);

        // Click to go back
        backBtn.setOnClickListener(v -> finish());

        // Get data from Intent
        materialId = getIntent().getIntExtra("id", -1);
        materialName = getIntent().getStringExtra("name");
        subject = getIntent().getStringExtra("subject");
        dueDate = getIntent().getStringExtra("due_date");
        dueTime = getIntent().getStringExtra("due_time");
        filePath = getIntent().getStringExtra("file_path");

        // Set data to fields
        editMaterialName.setText(materialName);
        editSubject.setText(subject);
        textDueDate.setText(dueDate);
        editTime.setText(dueTime);

        // Set up date picker
        iconCalendar.setOnClickListener(v -> openDatePicker());

        // File picker
        findViewById(R.id.chooseFileBtn).setOnClickListener(v -> openFilePicker());

        // Don't call API – only show Toast or prepare for future API
        doneBtn.setOnClickListener(v -> {
            Toast.makeText(this, "Ready to update:\nName: " + editMaterialName.getText().toString(), Toast.LENGTH_SHORT).show();
        });
    }

    private void openDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(this, (DatePicker view, int year, int month, int dayOfMonth) -> {
            String formattedDate = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year);
            textDueDate.setText(formattedDate);
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(Intent.createChooser(intent, "Select PDF"), PICK_PDF_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            fileUri = data.getData();
            Toast.makeText(this, "File selected", Toast.LENGTH_SHORT).show();
        }
    }

    // Optional: Helper if you want the real file path from Uri later
    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Files.FileColumns.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA);
            cursor.moveToFirst();
            String result = cursor.getString(column_index);
            cursor.close();
            return result;
        }
        return null;
    }
}
