package com.example.tasks.data.local.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.tasks.domain.models.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TasksDao {

    @Query("Select * from Tasks")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Upsert
    suspend fun upsertTask(task: TaskEntity)

    // needed to update the db with the new fetched data from the server
    @Upsert
    suspend fun upsertTasks(task: List<TaskEntity>)

}