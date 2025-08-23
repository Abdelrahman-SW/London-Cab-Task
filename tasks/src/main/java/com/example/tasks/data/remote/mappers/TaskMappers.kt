package com.example.tasks.data.remote.mappers

import com.example.tasks.data.remote.dto.TaskDto
import com.example.tasks.domain.models.Task

fun TaskDto.toTask(): Task {
    return Task(
        id = id,
        description = description,
        timeStamp = timeStamp
    )
}