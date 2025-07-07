package com.saveetha.studyplanner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ForgotPassword extends AppCompatActivity {
    Button btnContinue;
    Button btnCancel;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.forgotpassword);
        btnContinue = findViewById(R.id.btnContinue);

        btnContinue.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginPage.class));});

        btnCancel = findViewById(R.id.btCancel);

        btnCancel.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginPage.class));});
    }
}
