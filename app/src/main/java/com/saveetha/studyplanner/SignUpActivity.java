package com.saveetha.studyplanner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.saveetha.studyplanner.api.ApiClient;
import com.saveetha.studyplanner.api.ApiService;
import com.saveetha.studyplanner.api.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    EditText etUserName, etEmail, etNewPassword, etConfirmPassword;
    Button btnRegister, btCancel;
    TextView btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        etUserName = findViewById(R.id.etUserName);
        etEmail = findViewById(R.id.etEmail);
        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        btnRegister = findViewById(R.id.btnRegister);
        btCancel = findViewById(R.id.btCancel);
        btLogin = findViewById(R.id.btLogin);

        btnRegister.setOnClickListener(v -> registerUser());

        btCancel.setOnClickListener(v -> startActivity(new Intent(SignUpActivity.this, LoginPage.class)));

        btLogin.setOnClickListener(v -> startActivity(new Intent(SignUpActivity.this, LoginPage.class)));
    }

    private void registerUser() {
        String username = etUserName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etNewPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<LoginResponse> call = apiService.registerUser(username, email, password, confirmPassword);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isStatus()) {
                        Toast.makeText(SignUpActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUpActivity.this, LoginPage.class));
                        finish();
                    } else {
                        Toast.makeText(SignUpActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SignUpActivity.this, "Response Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
