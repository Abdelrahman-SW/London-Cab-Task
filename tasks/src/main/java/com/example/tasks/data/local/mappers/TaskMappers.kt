package com.example.tasks.data.local.mappers

import com.example.tasks.data.local.db.TaskEntity
import com.example.tasks.domain.models.Task

fun Task.toTaskEntity(): TaskEntity {
    return TaskEntity(
        description = description
    )
}


fun TaskEntity.toTask(): Task {
    return Task(
        description = description
    )
}