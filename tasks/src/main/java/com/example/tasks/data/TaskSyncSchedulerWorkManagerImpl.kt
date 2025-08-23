package com.example.tasks.data

import android.content.Context
import android.icu.util.TimeUnit
import androidx.compose.ui.unit.Constraints
import androidx.work.BackoffPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.await
import com.example.tasks.domain.TaskRepository
import com.example.tasks.domain.TaskSyncScheduler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.toJavaDuration

class TaskSyncSchedulerWorkManagerImpl(
    private val context: Context,
) : TaskSyncScheduler {

    private val workManager = WorkManager.getInstance(context)

    override suspend fun scheduleSyncingTask(duration: Duration) {
        val isSyncScheduled = withContext(Dispatchers.IO) {
            workManager
                .getWorkInfosByTag(TaskSyncScheduler.TaskSyncSchedulerTag).get().isNotEmpty()
        }
        if (isSyncScheduled) return
        val workRequest = PeriodicWorkRequestBuilder<FetchTasksWorker>(
            repeatInterval = duration.toJavaDuration()
        ).setConstraints(
            androidx.work.Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        )
            .setBackoffCriteria(
                backoffPolicy = BackoffPolicy.EXPONENTIAL,
                backoffDelay = 2000L,
                timeUnit = java.util.concurrent.TimeUnit.MILLISECONDS
            )
            .setInitialDelay(
                duration = 30,
                timeUnit = java.util.concurrent.TimeUnit.MINUTES
            )
            .build()
        workManager.enqueue(workRequest).await()
    }

    override suspend fun cancelSyncingTask() {
        WorkManager.getInstance(context = context)
            .cancelAllWorkByTag(TaskSyncScheduler.TaskSyncSchedulerTag).await()
    }

}