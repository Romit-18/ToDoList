package com.example.todolist;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String taskName = intent.getStringExtra("taskName");
        int taskId = intent.getIntExtra("taskId", 0); // Get task ID for unique notification

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "task_notifications";

        // Create the notification channel if API level is 26 or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Task Notifications", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        // Intent to open the main activity when notification is tapped
        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.putExtra("taskId", taskId); // Pass task ID to MainActivity
        PendingIntent pendingIntent = PendingIntent.getActivity(context, taskId, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Build and issue the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_notification) // Make sure to use a valid notification icon
                .setContentTitle("Task Reminder")
                .setContentText("Don't forget: " + taskName)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        notificationManager.notify(taskId, builder.build()); // Use taskId for unique notification
    }
}

