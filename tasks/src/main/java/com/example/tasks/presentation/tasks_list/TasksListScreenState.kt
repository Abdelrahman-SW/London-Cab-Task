package com.example.tasks.presentation.tasks_list

import com.example.tasks.domain.models.Task

data class TasksListScreenState(
    val isRefreshing : Boolean = false,
    val tasks : List<Task> = emptyList()
)
