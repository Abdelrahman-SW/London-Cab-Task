package com.example.tasks.domain

import com.example.core.domain.util.DataError
import com.example.core.domain.util.Result
import com.example.tasks.domain.models.Task
import kotlinx.coroutines.flow.Flow

interface RemoteTasksDataSource {
    suspend fun getAllTasks() : Result<List<Task>, DataError.Network>
    suspend fun postTask (task: Task) : Result<Unit, DataError.Network>
}