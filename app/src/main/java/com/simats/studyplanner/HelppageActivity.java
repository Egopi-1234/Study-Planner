package com.simats.studyplanner;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import androidx.activity.EdgeToEdge;

public class HelppageActivity extends AppCompatActivity {

    ImageView bkarr4;
    TextView number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_helppage);

        // Back arrow
        bkarr4 = findViewById(R.id.bkarr4);
        bkarr4.setOnClickListener(v -> finish());

        // Phone number click
        number = findViewById(R.id.number);
        number.setOnClickListener(v -> {
            String phone = "+919361878272"; // Your number
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phone));
            startActivity(intent);
        });
    }
}
