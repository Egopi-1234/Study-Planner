package com.saveetha.studyplanner;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PrivacypolicyPageActivity extends AppCompatActivity {

    ImageView backarrbt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_privacypolicy_page);

        backarrbt1 = findViewById(R.id.backarrbt1);
        backarrbt1.setOnClickListener(v -> {

            finish();
        });
    }
}