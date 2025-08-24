package com.example.tasks.data.repo

import com.example.core.domain.util.DataError
import com.example.core.domain.util.Result
import com.example.core.domain.util.map
import com.example.tasks.domain.datasources.LocalTasksDataSource
import com.example.tasks.domain.datasources.RemoteTasksDataSource
import com.example.tasks.domain.models.Task
import com.example.tasks.domain.repo.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow

/**
 * First Offline Repository responsible for Manage tasks & fetching tasks and syncing it with database .
 *
 * Combines local Room storage with remote Ktor API.
 * Implements offline-first logic.
 *
 * @property localTasksDataSource the database for tasks
 * @property remoteTasksDataSource the remote API service
 * @property applicationScope global scope to launch tasks that need to be completed even user pop screen
 */

class OfflineFirstTaskRepository(
    private val localTasksDataSource: LocalTasksDataSource,
    private val remoteTasksDataSource: RemoteTasksDataSource,
    private val applicationScope: CoroutineScope,
) : TaskRepository {

    /**
     * Fetch tasks from the remote API and update the local DB.
     *
     * If the network request fails, the error is wrapped in [Result.Error].
     * If successful, all local tasks are cleared and replaced with fresh data from the server.
     *
     * @return [Result.Success] if tasks were successfully fetched and stored,
     * or [Result.Error] if the request failed.
     */

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

    /**
     * Display Tasks From The Db after syncing it with the server (single source of truth).
     *
     * @return List of user created tasks
     */
    override fun getAllTasks(): Flow<List<Task>> {
        return localTasksDataSource.getAllTasks()
    }

    /**
     * Get a single task by its ID from the local DB.
     *
     * @param id The unique identifier of the task.
     * @return The matching [Task] object.
     */
    override suspend fun getTaskById(id: Int): Task {
        return localTasksDataSource.getTaskById(id)
    }

    /**
     * Insert or update a task both locally and remotely.
     *
     * First, the task is stored in the local DB.
     * Then, it is synced to the server. If the remote call fails,
     * the task remains stored locally and can be synced later.
     *
     * @param task The [Task] object to insert or update.
     * @return [Result.Success] if both local and remote succeeded,
     * or [Result.Error] if either operation failed.
     */

    override suspend fun upsertTask(task: Task): Result<Unit, DataError> {
        val localResult = localTasksDataSource.upsertTask(task)
        if (localResult !is Result.Success) {
            return localResult
        }
        // sync it with the server
        val remoteResult = remoteTasksDataSource.postTask(task)
        if (remoteResult !is Result.Success) {
            // TODO: should schedule work manager to try sync it later
            return remoteResult
        }
        return remoteResult
    }
}