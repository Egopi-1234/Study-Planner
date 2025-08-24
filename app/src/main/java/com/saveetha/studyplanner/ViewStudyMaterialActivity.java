package com.saveetha.studyplanner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewStudyMaterialActivity extends AppCompatActivity {

    private ImageView backArrow, bookImageView, zoomInBtn, zoomOutBtn;
    private EditText filePathEditText;
    private Button prevPageBtn, nextPageBtn;
    private TextView pageIndicator;

    private float scale = 1f;         // Initial zoom
    private final float scaleStep = 0.2f;  // Zoom step
    private final float maxScale = 3f;     // Maximum zoom
    private final float minScale = 0.5f;   // Minimum zoom

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewstudymaterialdetails);

        // Initialize views
        backArrow = findViewById(R.id.back_arrow);
        bookImageView = findViewById(R.id.bookImageView);
        filePathEditText = findViewById(R.id.filePathEditText);
        prevPageBtn = findViewById(R.id.prevPageBtn);
        nextPageBtn = findViewById(R.id.nextPageBtn);
        zoomInBtn = findViewById(R.id.zoomInBtn);
        zoomOutBtn = findViewById(R.id.zoomOutBtn);
        pageIndicator = findViewById(R.id.pageIndicator);

        // Back button click -> go back to previous activity
        backArrow.setOnClickListener(v -> onBackPressed());

        // Previous page button
        prevPageBtn.setOnClickListener(v -> finish());

        // Next page button
        nextPageBtn.setOnClickListener(v -> {
            Intent intent = new Intent(ViewStudyMaterialActivity.this, ViewStudyMaterialDetailPage2Activity.class);
            startActivity(intent);
        });

        // Zoom In
        zoomInBtn.setOnClickListener(v -> {
            if (scale < maxScale) {
                scale += scaleStep;
                bookImageView.setScaleX(scale);
                bookImageView.setScaleY(scale);
            }
        });

        // Zoom Out
        zoomOutBtn.setOnClickListener(v -> {
            if (scale > minScale) {
                scale -= scaleStep;
                bookImageView.setScaleX(scale);
                bookImageView.setScaleY(scale);
            }
        });
    }
}
