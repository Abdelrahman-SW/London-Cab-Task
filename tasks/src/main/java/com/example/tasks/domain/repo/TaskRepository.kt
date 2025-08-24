package com.example.tasks.domain.repo

import com.example.core.domain.util.DataError
import com.example.core.domain.util.Result
import com.example.tasks.domain.models.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    /**
     * Fetch tasks from the remote API and update the local DB.
     *
     * If the network request fails, the error is wrapped in [Result.Error].
     * If successful, all local tasks are cleared and replaced with fresh data from the server.
     *
     * @return [Result.Success] if tasks were successfully fetched and stored,
     * or [Result.Error] if the request failed.
     */
    suspend fun fetchTasks(): Result<Unit, DataError>

    /**
     * Display Tasks From The Db after syncing it with the server (single source of truth).
     *
     * @return List of user created tasks
     */
    fun getAllTasks(): Flow<List<Task>>

    /**
     * Get a single task by its ID from the local DB.
     *
     * @param id The unique identifier of the task.
     * @return The matching [Task] object.
     */
    suspend fun getTaskById(id: Int): Task

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
    suspend fun upsertTask(task: Task): Result<Unit, DataError>
}