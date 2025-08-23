package com.example.tasks.data.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tasks")
data class TaskEntity (
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val description : String,
    //val title : String , will added on v2 schema
    val timeStamp : Long
)