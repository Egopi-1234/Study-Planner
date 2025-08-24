package com.saveetha.studyplanner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;

public class viewstudymaterialdetailspage6Activity extends AppCompatActivity {

    private Button nextPageBtn6, prevPageBtn6;
    private ImageView bookImageView, zoomInBtn, zoomOutBtn;

    private float scale = 1f;            // Initial zoom
    private final float scaleStep = 0.2f;  // Zoom step
    private final float maxScale = 3f;     // Maximum zoom
    private final float minScale = 0.5f;   // Minimum zoom

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_viewstudymaterialdetailspage6);

        // Initialize views
        nextPageBtn6 = findViewById(R.id.nextPageBtn6);
        prevPageBtn6 = findViewById(R.id.prevPageBtn6);
        bookImageView = findViewById(R.id.bookImageView);
        zoomInBtn = findViewById(R.id.zoomInBtn);
        zoomOutBtn = findViewById(R.id.zoomOutBtn);

        // Next Page → Page 1
        nextPageBtn6.setOnClickListener(v -> {
            Intent intent = new Intent(viewstudymaterialdetailspage6Activity.this, ViewStudyMaterialActivity.class);
            startActivity(intent);
            finish();
        });

        // Previous Page → Page 5
        prevPageBtn6.setOnClickListener(v -> {
            Intent intent = new Intent(viewstudymaterialdetailspage6Activity.this, viewstudymaterialdetailspage5Activity.class);
            startActivity(intent);
            finish();
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
