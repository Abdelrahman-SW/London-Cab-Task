package com.example.tasks.data.local

import android.database.sqlite.SQLiteFullException
import com.example.core.domain.util.DataError
import com.example.core.domain.util.Result
import com.example.tasks.data.local.db.TasksDao
import com.example.tasks.data.local.mappers.toTask
import com.example.tasks.data.local.mappers.toTaskEntity
import com.example.tasks.domain.LocalTasksDataSource
import com.example.tasks.domain.models.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomLocalTaskDataSourceImpl(
    private val dao: TasksDao
) : LocalTasksDataSource {
    override fun getAllTasks(): Flow<List<Task>> {
        return dao.getAllTasks().map { it.map { taskEntity -> taskEntity.toTask() } }
    }

    override suspend fun getTaskById(id: Int): Task {
        return dao.getTaskById(id).toTask()
    }

    override suspend fun upsertTask(task: Task): Result<Unit, DataError.Local> {
        return try {
            dao.upsertTask(task.toTaskEntity())
            Result.Success(Unit)
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun upsertTasks(tasks: List<Task>): Result<Unit, DataError.Local> {
        return try {
            dao.upsertTasks(tasks.map { it.toTaskEntity() })
            Result.Success(Unit)
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteAllTasks() {
        dao.deleteAllTasks()
    }
}