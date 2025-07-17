package com.saveetha.studyplanner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.saveetha.studyplanner.api.ApiClient;
import com.saveetha.studyplanner.api.ApiService;
import com.saveetha.studyplanner.api.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPage extends AppCompatActivity {

    EditText emailEditText, passwordEditText;
    Button loginbtn;
    TextView forgotpassbtn, signupbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);

        emailEditText = findViewById(R.id.email_edit);       // Make sure these match your XML
        passwordEditText = findViewById(R.id.password_edit);
        loginbtn = findViewById(R.id.loginbtn);
        forgotpassbtn = findViewById(R.id.forgotpassbtn);
        signupbtn = findViewById(R.id.signupbtn);

        loginbtn.setOnClickListener(v -> loginUser());

        forgotpassbtn.setOnClickListener(v ->
                startActivity(new Intent(LoginPage.this, ForgotPassword.class)));

        signupbtn.setOnClickListener(v ->
                startActivity(new Intent(LoginPage.this, SignUpActivity.class)));
    }

    private void loginUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<LoginResponse> call = apiService.loginUser(email, password);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isStatus()) {
                        // Save user ID in SharedPreferences
                        int userId = response.body().getData().getId();

                        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("user_id", userId);
                        editor.apply();

                        Toast.makeText(LoginPage.this, "Welcome " + response.body().getData().getUsername(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginPage.this, WelcomeActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginPage.this, "Login Failed: " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginPage.this, "Response Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginPage.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
