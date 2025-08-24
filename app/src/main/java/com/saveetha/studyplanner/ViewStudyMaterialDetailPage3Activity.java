package com.saveetha.studyplanner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;

public class ViewStudyMaterialDetailPage3Activity extends AppCompatActivity {

    private Button nextPageBtn3, prevPageBtn3;
    private ImageView bookImageView, zoomInBtn, zoomOutBtn;

    private float scale = 1f;            // Initial zoom
    private final float scaleStep = 0.2f;  // Zoom step
    private final float maxScale = 3f;     // Maximum zoom
    private final float minScale = 0.5f;   // Minimum zoom

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_viewstudymaterialdetailspage3);

        // Initialize views
        nextPageBtn3 = findViewById(R.id.nextPageBtn3);
        prevPageBtn3 = findViewById(R.id.prevPageBtn3);
        bookImageView = findViewById(R.id.bookImageView);
        zoomInBtn = findViewById(R.id.zoomInBtn);
        zoomOutBtn = findViewById(R.id.zoomOutBtn);

        // Next Page (Page 4)
        nextPageBtn3.setOnClickListener(v -> {
            Intent intent = new Intent(ViewStudyMaterialDetailPage3Activity.this, viewstudymaterialdetailspage4Activity.class);
            startActivity(intent);
        });

        // Previous Page (Page 2)
        prevPageBtn3.setOnClickListener(v -> {
            Intent intent = new Intent(ViewStudyMaterialDetailPage3Activity.this, ViewStudyMaterialDetailPage2Activity.class);
            startActivity(intent);
            finish(); // Optional: close current activity
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
