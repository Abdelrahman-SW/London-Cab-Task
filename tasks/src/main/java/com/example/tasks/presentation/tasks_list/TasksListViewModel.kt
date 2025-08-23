package com.example.tasks.presentation.tasks_list


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.util.DataError
import com.example.core.domain.util.Result
import com.example.core.presentation.util.asUiText
import com.example.tasks.domain.TaskRepository
import com.example.tasks.presentation.tasks_list.TasksListScreenEvent.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class TasksListViewModel(
    private val taskRepository: TaskRepository
) : ViewModel() {

    var state by mutableStateOf(TasksListScreenState())
        private set

    private val _events = Channel<TasksListScreenEvent>()
    val events = _events.receiveAsFlow()


    init {
        // fetch tasks from the server
        fetchTasks()

        viewModelScope.launch {
            taskRepository.getAllTasks().collect {
                state = state.copy(tasks = it)
            }
        }
    }

    fun fetchTasks() {
        state = state.copy(isRefreshing = true)
        viewModelScope.launch {
            delay(3000L)
            val result = taskRepository.fetchTasks()
            when (result) {
                is Result.Error<DataError> -> {
                    state = state.copy(isRefreshing = false)
                    _events.trySend(TasksListScreenEvent.Error(result.error.asUiText()))
                }

                is Result.Success<*> -> {
                    state = state.copy(isRefreshing = false)
                }
            }
        }
    }


    fun onAction(event: TasksListScreenAction) {
        when (event) {
            TasksListScreenAction.OnAddNewTaskClicked -> {
                _events.trySend(NavigateToUpsertTask())
            }

            is TasksListScreenAction.OnTaskClicked -> {
                _events.trySend(NavigateToUpsertTask(event.taskId))
            }

            TasksListScreenAction.OnPullToRefresh -> {
                fetchTasks()
            }
        }
    }


}