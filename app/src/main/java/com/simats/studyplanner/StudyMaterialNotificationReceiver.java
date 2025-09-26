package com.simats.studyplanner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class StudyMaterialNotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String materialName = intent.getStringExtra("material_name");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "task_channel_id")
                .setSmallIcon(R.drawable.notification_bell)
                .setContentTitle("Study Material Reminder")
                .setContentText("Review material: " + materialName)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManagerCompat.from(context).notify((int) System.currentTimeMillis(), builder.build());
    }
}
