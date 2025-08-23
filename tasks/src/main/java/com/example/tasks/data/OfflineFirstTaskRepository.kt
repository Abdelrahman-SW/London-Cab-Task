package com.example.tasks.data

import com.example.core.domain.util.DataError
import com.example.core.domain.util.Result
import com.example.core.domain.util.map
import com.example.tasks.domain.LocalTasksDataSource
import com.example.tasks.domain.RemoteTasksDataSource
import com.example.tasks.domain.TaskRepository
import com.example.tasks.domain.models.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class OfflineFirstTaskRepository(
    private val localTasksDataSource: LocalTasksDataSource,
    private val remoteTasksDataSource: RemoteTasksDataSource,
    private val applicationScope: CoroutineScope,
) : TaskRepository {

    override suspend fun fetchTasks(): Result<Unit, DataError> {
        return when (val result = remoteTasksDataSource.getAllTasks()) {
            is Result.Error<DataError.Network> -> {
                result.map {  }
            }
            is Result.Success<List<Task>> -> {
                // update db
                applicationScope.async {
                    localTasksDataSource.deleteAllTasks()
                    localTasksDataSource.upsertTasks(result.data)
                }.await()
            }
        }
    }

    override fun getAllTasks(): Flow<List<Task>> {
        // single source of truth
        return localTasksDataSource.getAllTasks()
    }

    override suspend fun getTaskById(id: Int): Task {
        return localTasksDataSource.getTaskById(id)
    }

    override suspend fun upsertTask(task: Task): Result<Unit, DataError> {
        val localResult = localTasksDataSource.upsertTask(task)
        if (localResult !is Result.Success) {
            return localResult
        }
        // sync it with the server
        val remoteResult = remoteTasksDataSource.postTask(task)
        if (remoteResult !is Result.Success) {
            // should schedule work manager to try sync it later
            return remoteResult
        }
        return remoteResult
    }
}