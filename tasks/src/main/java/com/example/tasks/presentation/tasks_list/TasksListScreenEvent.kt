package com.example.tasks.presentation.tasks_list

import com.example.core.presentation.util.UiText

sealed interface TasksListScreenEvent {
    data class NavigateToUpsertTask(val taskId: Int = -1) : TasksListScreenEvent
    data object AfterLogout : TasksListScreenEvent
    data class Error(val error: UiText) : TasksListScreenEvent
}