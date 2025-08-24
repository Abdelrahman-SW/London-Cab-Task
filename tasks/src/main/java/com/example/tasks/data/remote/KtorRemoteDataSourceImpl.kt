package com.example.tasks.data.remote

import com.example.core.data.networking.ext.get
import com.example.core.domain.util.DataError
import com.example.core.domain.util.Result
import com.example.core.domain.util.map
import com.example.tasks.data.remote.dto.TaskDto
import com.example.tasks.data.remote.mappers.toTask
import com.example.tasks.domain.datasources.RemoteTasksDataSource
import com.example.tasks.domain.models.Task
import io.ktor.client.HttpClient
import kotlinx.coroutines.delay

class KtorRemoteDataSourceImpl(
    private val client: HttpClient,
) : RemoteTasksDataSource {
    override suspend fun getAllTasks(): Result<List<Task>, DataError.Network> {
        return client.get<List<TaskDto>>(
            route = "c/5ecd-b349-4ac1-af28"
        ).map { it.map { taskDto -> taskDto.toTask() } }
    }

    override suspend fun postTask(task: Task): Result<Unit, DataError.Network> {
        // simulate the data is updated successfully on the server
        delay(300L)
        return Result.Success(Unit)
    }
}