package com.example.tasks.data

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.core.domain.util.DataError
import com.example.tasks.domain.TaskRepository

class FetchTasksWorker(
    context: Context,
    params: WorkerParameters,
    private val taskRepository: TaskRepository

) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        if (runAttemptCount >= 5) {
            return Result.failure()
        }
        return when (val result = taskRepository.fetchTasks()) {
            is com.example.core.domain.util.Result.Error<DataError> -> {
                when (result.error) {
                    DataError.Local.DISK_FULL -> Result.failure()
                    DataError.Network.REQUEST_TIMEOUT -> Result.retry()
                    DataError.Network.UNAUTHORIZED -> Result.retry()
                    DataError.Network.INVALID_CREDENTIALS -> Result.failure()
                    DataError.Network.CONFLICT -> Result.retry()
                    DataError.Network.TOO_MANY_REQUESTS -> Result.retry()
                    DataError.Network.NO_INTERNET -> Result.retry()
                    DataError.Network.PAYLOAD_TOO_LARGE -> Result.failure()
                    DataError.Network.SERVER_ERROR -> Result.retry()
                    DataError.Network.SERIALIZATION -> Result.failure()
                    DataError.Network.UNKNOWN -> Result.failure()
                }
            }

            is com.example.core.domain.util.Result.Success<*> -> {
                Result.success()
            }
        }
    }
}