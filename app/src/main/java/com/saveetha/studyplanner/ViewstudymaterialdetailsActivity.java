package com.saveetha.studyplanner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ViewstudymaterialdetailsActivity extends AppCompatActivity {

    EditText materialNameEditText, subjectEditText, dueDateEditText, dueTimeEditText, filePathEditText;
    TextView editstudybtn;
    CheckBox taskCompletedCheckbox;
    LinearLayout deleteTaskLayout;
    ImageView backArrow;

    String materialName, subject, dueDate, dueTime, filePath;
    int materialId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewstudymaterialdetails);

        // Intent values
        materialId = getIntent().getIntExtra("id", -1);
        materialName = getIntent().getStringExtra("name");
        subject = getIntent().getStringExtra("subject");
        dueDate = getIntent().getStringExtra("due_date");
        dueTime = getIntent().getStringExtra("due_time");
        filePath = getIntent().getStringExtra("file_path");

        // Bind views
        materialNameEditText = findViewById(R.id.materialNameEditText);     // dynamically assign ID
        subjectEditText = findViewById(R.id.subjectEditText);
        dueDateEditText = findViewById(R.id.dueDateEditText);
        dueTimeEditText = findViewById(R.id.dueTimeEditText);
        filePathEditText = findViewById(R.id.filePathEditText);
        taskCompletedCheckbox = findViewById(R.id.task_completed_checkbox);
        editstudybtn = findViewById(R.id.editstudybtn);
        deleteTaskLayout = findViewById(R.id.material_delete_task_button);
        backArrow = findViewById(R.id.back_arrow); // Make sure you set android:id="@+id/back_arrow" in XML

        // Set values
        materialNameEditText.setText(materialName);
        subjectEditText.setText(subject);
        dueDateEditText.setText(dueDate);
        dueTimeEditText.setText(dueTime);
        filePathEditText.setText(filePath.substring(filePath.lastIndexOf("/") + 1));  // Just show filename

        // Back button
        backArrow.setOnClickListener(v -> finish());

        // Edit button click
        editstudybtn.setOnClickListener(v -> {
            Intent intent = new Intent(ViewstudymaterialdetailsActivity.this, EditstudymaterialActivity.class);
            intent.putExtra("id", materialId);
            startActivity(intent);
        });

        // Checkbox logic
        taskCompletedCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(this, "Task marked as completed!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Task marked as not completed", Toast.LENGTH_SHORT).show();
            }
        });

        // Delete task click (you can implement confirmation + Retrofit call here)
        deleteTaskLayout.setOnClickListener(v -> {
            Toast.makeText(this, "Delete task clicked!", Toast.LENGTH_SHORT).show();
            // TODO: Call delete API
        });
    }
}
