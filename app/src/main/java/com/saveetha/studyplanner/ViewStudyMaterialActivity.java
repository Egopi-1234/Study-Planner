package com.saveetha.studyplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewStudyMaterialActivity extends AppCompatActivity {

    TextView lesson1, lesson2, lesson3, lesson4, lesson5, lesson6;
    ImageView bkarrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewstudymaterialdetails);

        // Initialize TextViews
        lesson1 = findViewById(R.id.lesson1);
        lesson2 = findViewById(R.id.lesson2);
        lesson3 = findViewById(R.id.lesson3);
        lesson4 = findViewById(R.id.lesson4);
        lesson5 = findViewById(R.id.lesson5);
        lesson6 = findViewById(R.id.lesson6);

        // Initialize the back arrow ImageView
        bkarrow = findViewById(R.id.bkarrow);

        // Set click listener for back navigation
        bkarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tell HomepageActivity to open the "Study Material" fragment
                Intent intent = new Intent(ViewStudyMaterialActivity.this, HomepageActivity.class);
                intent.putExtra("openFragment", "material"); // pass key to HomepageActivity
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish(); // Close current activity so it doesn’t stack
            }
        });

        // Lesson click listeners
        lesson1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewStudyMaterialActivity.this,
                        ViewStudyMaterialDetailPage2Activity.class));
            }
        });

        lesson2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewStudyMaterialActivity.this,
                        ViewStudyMaterialDetailPage3Activity.class));
            }
        });

        lesson3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewStudyMaterialActivity.this,
                        viewstudymaterialdetailpage4Activity.class));
            }
        });

        lesson4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewStudyMaterialActivity.this,
                        viewstudymaterialdetailspage5Activity.class));
            }
        });

        lesson5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewStudyMaterialActivity.this,
                        viewstudymaterialdetailspage6Activity.class));
            }
        });

        lesson6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewStudyMaterialActivity.this,
                        mcqtestActivity.class));
            }
        });
    }
}
