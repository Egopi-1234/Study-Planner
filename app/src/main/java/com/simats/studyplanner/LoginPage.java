package com.simats.studyplanner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.messaging.FirebaseMessaging;
import com.simats.studyplanner.api.ApiClient;
import com.simats.studyplanner.api.ApiService;
import com.simats.studyplanner.api.LoginResponse;

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

        emailEditText = findViewById(R.id.email_edit);
        passwordEditText = findViewById(R.id.password_edit);
        loginbtn = findViewById(R.id.loginbtn);
        forgotpassbtn = findViewById(R.id.forgotpassbtn);
        signupbtn = findViewById(R.id.signupbtn);

        // ✅ Auto-login if user already logged in
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        int savedUserId = sharedPreferences.getInt("user_id", -1);
        if (savedUserId != -1) {
            startActivity(new Intent(LoginPage.this, WelcomeActivity.class));
            finish();
            return;
        }

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
                        int userId = response.body().getData().getId();

                        // ✅ Save login details in SharedPreferences
                        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("user_id", userId);
                        editor.putString("email", response.body().getData().getEmail());
                        editor.putString("name", response.body().getData().getUsername());
                        editor.apply();

                        // ✅ Fetch FCM token and send to server
                        FirebaseMessaging.getInstance().getToken()
                                .addOnCompleteListener(task -> {
                                    if (!task.isSuccessful()) {
                                        startWelcomeActivity();
                                        return;
                                    }

                                    String fcmToken = task.getResult();
                                    editor.putString("fcm_token", fcmToken);
                                    editor.apply();

                                    // Send FCM token to server
                                    updateDeviceTokenOnServer(userId, fcmToken);

                                    startWelcomeActivity();
                                });
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

    private void startWelcomeActivity() {
        startActivity(new Intent(LoginPage.this, WelcomeActivity.class));
        finish();
    }

    private void updateDeviceTokenOnServer(int userId, String token) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.updateDeviceToken(userId, token)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        // Log for debug
                        Log.d("FCM", "Device token updated, code: " + response.code());
                        startWelcomeActivity(); // ✅ Move here
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e("FCM", "Failed to update device token: " + t.getMessage());
                        startWelcomeActivity(); // Move here as fallback
                    }
                });
    }

}
