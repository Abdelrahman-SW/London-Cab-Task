package com.example.tasks.presentation.tasks_list

import android.graphics.Paint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.room.util.TableInfo
import com.example.core.presentation.util.ObserveAsEvents
import com.example.core.presentation.util.ext.showToast
import com.example.tasks.presentation.tasks_list.components.TaskItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun TasksListScreenRoot(
    modifier: Modifier = Modifier,
    onNavigateToUpsertTask: (Int) -> Unit,
    taskListViewModel: TasksListViewModel = koinViewModel()
) {
    val context = LocalContext.current

    ObserveAsEvents(taskListViewModel.events) {
        when (it) {
            is TasksListScreenEvent.Error -> {
                context.showToast(it.error)
            }

            is TasksListScreenEvent.NavigateToUpsertTask -> {
                onNavigateToUpsertTask(it.taskId)
            }
        }
    }
    TaskListScreen(
        modifier = modifier,
        tasksListScreenState = taskListViewModel.state,
        onAction = taskListViewModel::onAction,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    modifier: Modifier = Modifier,
    tasksListScreenState: TasksListScreenState,
    onAction: (TasksListScreenAction) -> Unit
) {

    val pullToRefreshState = rememberPullToRefreshState()


    PullToRefreshBox(
        modifier = Modifier.fillMaxSize(),
        state = pullToRefreshState,
        isRefreshing = tasksListScreenState.isRefreshing,
        onRefresh = { onAction(TasksListScreenAction.OnPullToRefresh) }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(bottom = 16.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth().weight(1f),
                contentPadding = PaddingValues(24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp) ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (tasksListScreenState.tasks.isEmpty() && !tasksListScreenState.isRefreshing) {
                    item {
                        Box(
                            modifier = Modifier.fillParentMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No Tasks Yet")
                        }
                    }
                } else {
                    items(tasksListScreenState.tasks) {
                        TaskItem(task = it, onTaskClicked = { task ->
                            onAction(TasksListScreenAction.OnTaskClicked(task.id))
                        })
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .height(56.dp),
                onClick = {
                    onAction(TasksListScreenAction.OnAddNewTaskClicked)
                }
            ) {
                Text("Add New Task")
            }
        }
    }
}