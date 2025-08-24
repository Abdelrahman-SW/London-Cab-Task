package com.example.core.data.workmanager

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class DailyNotificationWorker(
    context: Context,
    params: WorkerParameters,

) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        if (runAttemptCount >= 5) {
            return Result.failure()
        }


        // show daily notification to remember user to create tasks
        val channelId = "task_reminder_channel"
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create channel for Android O+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Task Reminders",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications to remind user to create tasks"
            }
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.ic_popup_reminder)
            .setContentTitle("Hey !")
            .setContentText("Do You Want To Create Some Tasks ?")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)


       return Result.success()
    }
}