package com.example.core.domain

import kotlin.time.Duration

interface DailyNotificationScheduler {
    suspend fun scheduleDailyNotification (duration: Duration)
    suspend fun cancelDailyNotification ()

    companion object {
        const val DAILY_NOTIFICATION_TAG = "daily_notification"
    }
}