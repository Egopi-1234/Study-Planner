package com.simats.studyplanner;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class OpeningPageActivity extends AppCompatActivity {

    private static final int REQUEST_NOTIFICATION_PERMISSION = 100;

    Button getStarted, subscribeBtn;
    BillingManager billingManager;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_opening_page);

        // Handle system bar insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Request notification permission
        requestNotificationPermissionForAllVersions();

        // Auto-login if user already logged in
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        int savedUserId = sharedPreferences.getInt("user_id", -1);
        if (savedUserId != -1) {
            startActivity(new Intent(OpeningPageActivity.this, WelcomeActivity.class));
            finish();
            return;
        }

        // Initialize BillingManager
        billingManager = new BillingManager(this);
        billingManager.startConnection();

        // Get Started button
        getStarted = findViewById(R.id.getStarted);
        getStarted.setOnClickListener(v -> startActivity(new Intent(this, LoginPage.class)));

        // Subscribe Monthly button
        subscribeBtn = findViewById(R.id.subscribeBtn);
        subscribeBtn.setOnClickListener(v -> billingManager.launchPurchaseFlow());
    }

    // Notification permission for all Android versions
    private void requestNotificationPermissionForAllVersions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        REQUEST_NOTIFICATION_PERMISSION
                );
            } else {
                Toast.makeText(this, "Notification permission already granted", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (!NotificationManagerCompat.from(this).areNotificationsEnabled()) {
                showNotificationSettingsDialog();
            } else {
                Toast.makeText(this, "Notifications already enabled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Show settings dialog for notifications
    private void showNotificationSettingsDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Enable Notifications")
                .setMessage("We need notification access to alert you about your tasks. Please enable notifications.")
                .setPositiveButton("Go to Settings", (dialog, which) -> {
                    Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                            .putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                    startActivity(intent);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_NOTIFICATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Notification permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Notification permission denied. Notifications may not appear.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
