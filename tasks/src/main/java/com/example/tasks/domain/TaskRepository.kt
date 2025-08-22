package com.example.tasks.domain

import com.example.core.domain.util.DataError
import com.example.core.domain.util.Result
import com.example.tasks.domain.models.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun fetchTasks () : Result<Unit , DataError>
    fun getAllTasks() : Flow<List<Task>>
    suspend fun upsertTask(task: Task) : Result<Unit, DataError>
}