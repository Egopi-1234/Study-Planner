package com.saveetha.studyplanner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {
    Button btnRegister;
    Button btCancel;
    TextView btLogin;  // TextView because btLogin is TextView in XML

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(v -> {
            startActivity(new Intent(SignUpActivity.this, LoginPage.class));
        });

        btCancel = findViewById(R.id.btCancel);
        btCancel.setOnClickListener(v -> {
            startActivity(new Intent(SignUpActivity.this, LoginPage.class));
        });

        btLogin = findViewById(R.id.btLogin);
        btLogin.setOnClickListener(v -> {
            startActivity(new Intent(SignUpActivity.this, LoginPage.class));
        });
    }
}
