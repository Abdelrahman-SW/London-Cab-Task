package com.example.tasks.domain

import com.example.core.domain.util.DataError
import com.example.core.domain.util.Result
import com.example.tasks.domain.models.Task
import kotlinx.coroutines.flow.Flow

interface LocalTasksDataSource {
    fun getAllTasks() : Flow<List<Task>>
    suspend fun getTaskById(id : Int) : Task
    suspend fun upsertTask (task: Task) : Result<Unit , DataError.Local>
    suspend fun upsertTasks (tasks: List<Task>) : Result<Unit , DataError.Local>
    suspend fun deleteAllTasks()
}