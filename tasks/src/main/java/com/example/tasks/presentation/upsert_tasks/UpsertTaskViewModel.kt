package com.example.tasks.presentation.upsert_tasks


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.util.DataError
import com.example.core.domain.util.Result
import com.example.core.presentation.util.asUiText
import com.example.tasks.domain.TaskRepository
import com.example.tasks.domain.models.Task
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class UpsertTaskViewModel(
    private val taskRepository: TaskRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(UpsertTaskScreenState())
        private set

    private val _events = Channel<UpsertTaskScreenEvent>()
    val events = _events.receiveAsFlow()


    init {
        val taskId: Int? = savedStateHandle["taskId"] as Int?
        taskId?.let {
            if (taskId != -1) {
                // this is update screen task
                viewModelScope.launch {
                    state = state.copy(isLoading = true)
                    val task = taskRepository.getTaskById(taskId)
                    state = state.copy(
                        currentTask = task,
                        isLoading = false,
                        taskTitleTextField = task.description
                    )
                }
            }
        }
    }

    fun onAction(event: UpsertTaskScreenAction) {
        when (event) {
            is UpsertTaskScreenAction.OnTaskTitleChanged -> {
                state = state.copy(taskTitleTextField = event.title)
            }

            UpsertTaskScreenAction.OnUpsertTaskClicked -> {
                viewModelScope.launch {
                    val result = taskRepository.upsertTask(
                        task = state.currentTask?.copy(description = state.taskTitleTextField)
                            ?: Task(
                                description = state.taskTitleTextField,
                                timeStamp = System.currentTimeMillis()
                            )
                    )
                    when (result) {
                        is Result.Error<DataError> -> {
                            if (result.error is DataError.Local) {
                                _events.trySend(UpsertTaskScreenEvent.Error(result.error.asUiText()))
                            } else {
                                _events.trySend(UpsertTaskScreenEvent.Error(result.error.asUiText()))
                                // it can by synced with server but u should make it as a success
                                _events.trySend(UpsertTaskScreenEvent.OnTaskUpserted)
                            }
                        }

                        is Result.Success<*> -> {
                            _events.trySend(UpsertTaskScreenEvent.OnTaskUpserted)
                        }
                    }
                }
            }
        }
    }


}