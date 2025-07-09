package com.saveetha.studyplanner;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class edittaskpageActivity extends AppCompatActivity {

    ImageView baarr;
    ImageView donearr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edittaskpage);
        baarr = findViewById(R.id.baarr);
        baarr.setOnClickListener(v -> finish());

        donearr = findViewById(R.id.donearr);
        donearr.setOnClickListener(v -> finish());
    }
}
