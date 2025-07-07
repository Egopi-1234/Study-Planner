package com.saveetha.studyplanner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginPage extends AppCompatActivity {
    Button loginbtn;
    TextView forgotpassbtn;
    TextView signupbtn;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);

        loginbtn = findViewById(R.id.loginbtn);
        forgotpassbtn = findViewById(R.id.forgotpassbtn);
        signupbtn = findViewById(R.id.signupbtn);

        loginbtn.setOnClickListener(v -> {
            startActivity(new Intent(LoginPage.this, WelcomeActivity.class));
        });

        forgotpassbtn.setOnClickListener(v -> {
            startActivity(new Intent(LoginPage.this, ForgotPassword.class));
        });

        signupbtn.setOnClickListener(v -> {
            startActivity(new Intent(LoginPage.this, SignUpActivity.class));
        });
    }
}
