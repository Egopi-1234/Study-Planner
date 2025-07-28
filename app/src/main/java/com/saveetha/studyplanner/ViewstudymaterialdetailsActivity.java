package com.saveetha.studyplanner;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.webkit.MimeTypeMap;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.saveetha.studyplanner.api.ApiClient;
import com.saveetha.studyplanner.api.ApiService;
import com.saveetha.studyplanner.api.DeleteMaterialResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ViewstudymaterialdetailsActivity extends AppCompatActivity {

    EditText materialNameEditText, subjectEditText, dueDateEditText, dueTimeEditText, filePathEditText;
    TextView editstudybtn;
    CheckBox taskCompletedCheckbox;
    LinearLayout deleteTaskLayout;
    ImageView backArrow;

    String materialName, subject, dueDate, dueTime, filePath;
    int materialId, userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewstudymaterialdetails);

        // Read user ID from SharedPreferences saved during login
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        userId = sharedPreferences.getInt("user_id", -1);

        // Get material details from Intent extras
        materialId = getIntent().getIntExtra("id", -1);
        materialName = getIntent().getStringExtra("name");
        subject = getIntent().getStringExtra("subject");
        dueDate = getIntent().getStringExtra("due_date");
        dueTime = getIntent().getStringExtra("due_time");
        filePath = getIntent().getStringExtra("file_path"); // URL string

        // Bind views
        materialNameEditText = findViewById(R.id.materialNameEditText);
        subjectEditText = findViewById(R.id.subjectEditText);
        dueDateEditText = findViewById(R.id.dueDateEditText);
        dueTimeEditText = findViewById(R.id.dueTimeEditText);
        filePathEditText = findViewById(R.id.filePathEditText);
        taskCompletedCheckbox = findViewById(R.id.task_completed_checkbox);
        editstudybtn = findViewById(R.id.editstudybtn);
        deleteTaskLayout = findViewById(R.id.material_delete_task_button);
        backArrow = findViewById(R.id.back_arrow);

        // Set values in UI fields
        materialNameEditText.setText(materialName);
        subjectEditText.setText(subject);
        dueDateEditText.setText(dueDate);
        dueTimeEditText.setText(dueTime);
        filePathEditText.setText(filePath != null ? filePath.substring(filePath.lastIndexOf("/") + 1) : "");

        // Enable clicking file path to download and open
        filePathEditText.setEnabled(true);
        filePathEditText.setFocusable(false);
        filePathEditText.setOnClickListener(v -> {
            if (filePath != null && !filePath.isEmpty()) {
                downloadAndOpenFile(ApiClient.getBaseUrl() + filePath);
            } else {
                Toast.makeText(this, "No file URL available", Toast.LENGTH_SHORT).show();
            }
        });

        // Back arrow closes activity
        backArrow.setOnClickListener(v -> finish());

        // Edit button opens EditStudyMaterialActivity with current data
        editstudybtn.setOnClickListener(v -> {
            Intent intent = new Intent(ViewstudymaterialdetailsActivity.this, EditStudyMaterialActivity.class);
            intent.putExtra("id", materialId);
            intent.putExtra("name", materialName);
            intent.putExtra("subject", subject);
            intent.putExtra("due_date", dueDate);
            intent.putExtra("due_time", dueTime);
            intent.putExtra("file_path", filePath);
            startActivity(intent);
        });

        // Checkbox shows toast on checked change
        taskCompletedCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(this, isChecked ? "Task marked as completed!" : "Task marked as not completed", Toast.LENGTH_SHORT).show();
        });

        // Delete button calls delete API via Retrofit
        deleteTaskLayout.setOnClickListener(v -> {
            if (userId == -1) {
                Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(this, "Deleting material...", Toast.LENGTH_SHORT).show();

            // Create ApiService instance from your existing ApiClient
            Retrofit retrofit = ApiClient.getClient();
            ApiService apiService = retrofit.create(ApiService.class);

            Call<DeleteMaterialResponse> call = apiService.deleteMaterial(materialId, userId);
            call.enqueue(new Callback<DeleteMaterialResponse>() {
                @Override
                public void onResponse(Call<DeleteMaterialResponse> call, Response<DeleteMaterialResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        DeleteMaterialResponse deleteResponse = response.body();
                        if (deleteResponse.isStatus()) {
                            Toast.makeText(ViewstudymaterialdetailsActivity.this, deleteResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            finish(); // close after delete success
                        } else {
                            Toast.makeText(ViewstudymaterialdetailsActivity.this, "Failed: " + deleteResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ViewstudymaterialdetailsActivity.this, "Response failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<DeleteMaterialResponse> call, Throwable t) {
                    Toast.makeText(ViewstudymaterialdetailsActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void downloadAndOpenFile(String url) {
        try {
            String fileName = url.substring(url.lastIndexOf('/') + 1);
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            request.setTitle("Downloading " + fileName);
            request.setDescription("Downloading material...");
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, fileName);

            DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            long downloadId = manager.enqueue(request);

            BroadcastReceiver onComplete = new BroadcastReceiver() {
                public void onReceive(Context ctxt, Intent intent) {
                    long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                    if (downloadId == id) {
                        Uri uri = manager.getUriForDownloadedFile(downloadId);
                        openDownloadedFile(uri);
                        unregisterReceiver(this);
                    }
                }
            };

            registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        } catch (Exception e) {
            Toast.makeText(this, "Download failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void openDownloadedFile(Uri uri) {
        try {
            String mimeType = getContentResolver().getType(uri);
            if (mimeType == null) {
                mimeType = getMimeType(uri.toString());
            }

            Intent openIntent = new Intent(Intent.ACTION_VIEW);
            openIntent.setDataAndType(uri, mimeType);
            openIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(openIntent, "Open file with"));
        } catch (Exception e) {
            Toast.makeText(this, "Unable to open file", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private String getMimeType(String url) {
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.toLowerCase());
    }
}
