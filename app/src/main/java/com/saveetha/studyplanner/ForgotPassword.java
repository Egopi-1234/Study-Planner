package com.saveetha.studyplanner;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.saveetha.studyplanner.api.ApiClient;
import com.saveetha.studyplanner.api.ApiService;
import com.saveetha.studyplanner.api.ForgotPasswordResponse;
import com.saveetha.studyplanner.api.OtpResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassword extends AppCompatActivity {

    private EditText etEmail, etOtp, etNewPassword, etConfirmPassword;
    private Button btnSendOtp, btnContinue, btnCancel;

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpassword);
        // replace with your XML filename

        etEmail = findViewById(R.id.etEmail);
        etOtp = findViewById(R.id.etOtp);
        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        btnSendOtp = findViewById(R.id.btnSendOtp);
        btnContinue = findViewById(R.id.btnContinue);
        btnCancel = findViewById(R.id.btCancel);

        apiService = ApiClient.getClient().create(ApiService.class);

        btnSendOtp.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();

            if (email.isEmpty()) {
                etEmail.setError("Please enter email");
                return;
            }

            sendOtp(email);
        });

        btnContinue.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String otp = etOtp.getText().toString().trim();
            String newPassword = etNewPassword.getText().toString().trim();
            String confirmPassword = etConfirmPassword.getText().toString().trim();

            if (email.isEmpty()) {
                etEmail.setError("Please enter email");
                return;
            }
            if (otp.isEmpty()) {
                etOtp.setError("Please enter OTP");
                return;
            }
            if (newPassword.isEmpty()) {
                etNewPassword.setError("Please enter new password");
                return;
            }
            if (confirmPassword.isEmpty()) {
                etConfirmPassword.setError("Please confirm new password");
                return;
            }
            if (!newPassword.equals(confirmPassword)) {
                etConfirmPassword.setError("Passwords do not match");
                return;
            }

            resetPassword(email, otp, newPassword, confirmPassword);
        });

        btnCancel.setOnClickListener(v -> finish());
    }

    private void sendOtp(String email) {
        Call<OtpResponse> call = apiService.sendOtp(email);

        call.enqueue(new Callback<OtpResponse>() {
            @Override
            public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    OtpResponse otpResponse = response.body();
                    if (otpResponse.status) {
                        Toast.makeText(ForgotPassword.this, otpResponse.message, Toast.LENGTH_SHORT).show();
                        // Optionally auto-fill OTP for testing only (remove in production)
                        // etOtp.setText(String.valueOf(otpResponse.data.otp));
                    } else {
                        Toast.makeText(ForgotPassword.this, otpResponse.message, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ForgotPassword.this, "Failed to send OTP", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OtpResponse> call, Throwable t) {
                Toast.makeText(ForgotPassword.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void resetPassword(String email, String otp, String newPassword, String confirmPassword) {
        Call<ForgotPasswordResponse> call = apiService.resetPassword(email, otp, newPassword, confirmPassword);

        call.enqueue(new Callback<ForgotPasswordResponse>() {
            @Override
            public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ForgotPasswordResponse fpResponse = response.body();
                    if (fpResponse.status) {
                        Toast.makeText(ForgotPassword.this, fpResponse.message, Toast.LENGTH_SHORT).show();
                        finish(); // Close activity or redirect to login
                    } else {
                        Toast.makeText(ForgotPassword.this, fpResponse.message, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ForgotPassword.this, "Failed to reset password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {
                Toast.makeText(ForgotPassword.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
