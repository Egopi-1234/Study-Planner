package com.simats.studyplanner;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class TermsandconditionpageActivity extends AppCompatActivity {
   ImageView backarrbt1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_termsandconditionpage);

        backarrbt1 = findViewById(R.id.backarrbt1);
        backarrbt1.setOnClickListener(v -> {

            finish();
        });

    }
}