package com.example.tasks.domain

import kotlin.time.Duration

interface TaskSyncScheduler {
    suspend fun scheduleSyncingTask (duration: Duration)
    suspend fun cancelSyncingTask ()

    companion object {
        const val TASK_SYNC_TAG = "taskSync"
    }
}