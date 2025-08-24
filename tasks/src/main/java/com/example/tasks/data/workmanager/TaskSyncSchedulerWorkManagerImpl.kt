package com.example.tasks.data.workmanager

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.await
import com.example.tasks.domain.TaskSyncScheduler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import kotlin.time.Duration
import kotlin.time.toJavaDuration

class TaskSyncSchedulerWorkManagerImpl(
    private val context: Context,
) : TaskSyncScheduler {

    private val workManager = WorkManager.getInstance(context)

    override suspend fun scheduleSyncingTask(duration: Duration) {
        val isSyncScheduled = withContext(Dispatchers.IO) {
            workManager
                .getWorkInfosByTag(TaskSyncScheduler.TASK_SYNC_TAG).get().isNotEmpty()
        }
        if (isSyncScheduled) return
        val workRequest = PeriodicWorkRequestBuilder<FetchTasksWorker>(
            repeatInterval = duration.toJavaDuration()
        ).setConstraints(
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        )
            .setBackoffCriteria(
                backoffPolicy = BackoffPolicy.EXPONENTIAL,
                backoffDelay = 2000L,
                timeUnit = TimeUnit.MILLISECONDS
            )
            .setInitialDelay(
                duration = 30,
                timeUnit = TimeUnit.MINUTES
            )
            .addTag(TaskSyncScheduler.TASK_SYNC_TAG)
            .build()
        workManager.enqueue(workRequest).await()
    }

    override suspend fun cancelSyncingTask() {
        WorkManager.getInstance(context = context)
            .cancelAllWorkByTag(TaskSyncScheduler.TASK_SYNC_TAG).await()
    }

}