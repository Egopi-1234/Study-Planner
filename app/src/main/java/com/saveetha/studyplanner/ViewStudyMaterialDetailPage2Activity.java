package com.saveetha.studyplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;

public class ViewStudyMaterialDetailPage2Activity extends AppCompatActivity {

    private Button nextPageBtn2, prevPageBtn2;
    private ImageView bookImageView, zoomInBtn, zoomOutBtn;

    private float scale = 1f;           // Initial zoom
    private final float scaleStep = 0.2f;  // Zoom step
    private final float maxScale = 3f;     // Maximum zoom
    private final float minScale = 0.5f;   // Minimum zoom

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_viewstudymaterialdetailspage2);

        // Initialize views
        nextPageBtn2 = findViewById(R.id.nextPageBtn2);
        prevPageBtn2 = findViewById(R.id.prevPageBtn2);
        bookImageView = findViewById(R.id.bookImageView);
        zoomInBtn = findViewById(R.id.zoomInBtn);
        zoomOutBtn = findViewById(R.id.zoomOutBtn);

        // Next Page (Page 3)
        nextPageBtn2.setOnClickListener(v -> {
            Intent intent = new Intent(ViewStudyMaterialDetailPage2Activity.this, ViewStudyMaterialDetailPage3Activity.class);
            startActivity(intent);
        });

        // Previous Page (Page 1)
        prevPageBtn2.setOnClickListener(v -> {
            Intent intent = new Intent(ViewStudyMaterialDetailPage2Activity.this, ViewStudyMaterialActivity.class);
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
