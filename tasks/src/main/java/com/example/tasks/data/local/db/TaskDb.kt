package com.example.tasks.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tasks.data.local.db.dao.TasksDao
import com.example.tasks.data.local.db.entities.TaskEntity

@Database(entities = [TaskEntity::class], version = 1)
abstract class TaskDb : RoomDatabase() {
    abstract fun taskDao(): TasksDao

    companion object {
        const val DATABASE_NAME: String = "tasks_db"
    }
}