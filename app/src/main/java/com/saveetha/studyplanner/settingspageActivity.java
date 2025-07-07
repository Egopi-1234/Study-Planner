package com.saveetha.studyplanner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class settingspageActivity extends AppCompatActivity {

    private TextView conarrow5;
    private TextView conarrow8;
    private TextView conarrow7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settingspage);

        conarrow5 = findViewById(R.id.conarrow5);
        conarrow8 = findViewById(R.id.conarrow8);
        conarrow7 = findViewById(R.id.conarrow7);

        // Click to open Account Details page
        conarrow5.setOnClickListener(v -> {
            startActivity(new Intent(settingspageActivity.this, AccountdetailspageActivity.class));
        });

        // Click to open About page
        conarrow8.setOnClickListener(v -> {
            startActivity(new Intent(settingspageActivity.this, aboutpageActivity.class));
        });

        // Click to open Help page
        conarrow7.setOnClickListener(v -> {
            startActivity(new Intent(settingspageActivity.this, HelppageActivity.class));
        });
    }
}
