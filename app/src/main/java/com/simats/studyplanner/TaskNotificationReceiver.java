package com.simats.studyplanner;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class TaskNotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String taskName = intent.getStringExtra("task_name");
        String taskDescription = intent.getStringExtra("task_description");

        // Create notification channel on Android O+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "task_channel_id",
                    "Task Reminder Channel",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Channel for task reminders");

            NotificationManager notificationManagerSystem =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManagerSystem.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "task_channel_id")
                .setSmallIcon(android.R.drawable.ic_dialog_info)  // Replace with your own icon if you want
                .setContentTitle("Task Reminder: " + taskName)
                .setContentText(taskDescription != null ? taskDescription : "")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(taskDescription)) // Show full description
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        int notificationId = (int) (System.currentTimeMillis() & 0xfffffff);
        notificationManager.notify(notificationId, builder.build());
    }
}
