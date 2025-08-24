package com.example.tasks.data.local.mappers

import com.example.tasks.data.local.db.entities.TaskEntity
import com.example.tasks.domain.models.Task

fun Task.toTaskEntity(): TaskEntity {
    return TaskEntity(
        id = id,
        description = description,
        timeStamp = timeStamp
    )
}


fun TaskEntity.toTask(): Task {
    return Task(
        id = id,
        description = description,
        timeStamp = timeStamp
    )
}