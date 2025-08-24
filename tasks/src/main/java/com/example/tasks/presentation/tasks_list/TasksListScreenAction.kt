package com.example.tasks.presentation.tasks_list

sealed interface TasksListScreenAction {
    data class OnTaskClicked(val taskId : Int) : TasksListScreenAction
    data object OnAddNewTaskClicked : TasksListScreenAction
    data object OnPullToRefresh : TasksListScreenAction
    data object onLogoutClicked : TasksListScreenAction
}