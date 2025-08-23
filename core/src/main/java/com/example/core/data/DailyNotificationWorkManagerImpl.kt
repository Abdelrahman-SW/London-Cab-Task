package com.example.core.data

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.await
import com.example.core.domain.DailyNotificationScheduler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.time.Duration
import kotlin.time.toJavaDuration

class DailyNotificationWorkManagerImpl(
    private val context: Context,
) : DailyNotificationScheduler {

    private val workManager = WorkManager.getInstance(context)

    override suspend fun scheduleDailyNotification(duration: Duration) {
        val isSyncScheduled = withContext(Dispatchers.IO) {
            workManager
                .getWorkInfosByTag(DailyNotificationScheduler.DAILY_NOTIFICATION_TAG).get()
                .isNotEmpty()
        }
        if (isSyncScheduled) return
        val workRequest = PeriodicWorkRequestBuilder<DailyNotificationWorker>(
            repeatInterval = duration.toJavaDuration()
        )
            .setBackoffCriteria(
                backoffPolicy = BackoffPolicy.EXPONENTIAL,
                backoffDelay = 2000L,
                timeUnit = java.util.concurrent.TimeUnit.MILLISECONDS
            )
            .setInitialDelay(
                duration = 15,
                timeUnit = java.util.concurrent.TimeUnit.MINUTES
            )
            .build()
        workManager.enqueue(workRequest).await()
    }

    override suspend fun cancelDailyNotification() {
        WorkManager.getInstance(context = context)
            .cancelAllWorkByTag(DailyNotificationScheduler.DAILY_NOTIFICATION_TAG).await()
    }

}