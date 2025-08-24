package com.example.tasks.presentation.tasks_list

import android.app.Activity
import android.graphics.Paint
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.room.util.TableInfo
import com.example.core.presentation.util.ObserveAsEvents
import com.example.core.presentation.util.ext.showToast
import com.example.tasks.presentation.tasks_list.components.TaskItem
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.koin.androidx.compose.koinViewModel

@Composable
fun TasksListScreenRoot(
    modifier: Modifier = Modifier,
    onNavigateToUpsertTask: (Int) -> Unit,
    taskListViewModel: TasksListViewModel = koinViewModel(),
    onOpenAnalyticsPageButtonClicked: () -> Unit,
    afterLogout: () -> Unit,
    showAnalyticsFeature : Boolean = true
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

            TasksListScreenEvent.AfterLogout -> {
                afterLogout()
            }
        }
    }
    TaskListScreen(
        modifier = modifier,
        tasksListScreenState = taskListViewModel.state,
        onAction = taskListViewModel::onAction,
        onOpenAnalyticsPageButtonClicked = onOpenAnalyticsPageButtonClicked,
        showAnalyticsFeature = showAnalyticsFeature
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    modifier: Modifier = Modifier,
    tasksListScreenState: TasksListScreenState,
    onAction: (TasksListScreenAction) -> Unit,
    onOpenAnalyticsPageButtonClicked: () -> Unit,
    showAnalyticsFeature : Boolean = false
) {

    var expanded by rememberSaveable { mutableStateOf(false) }
    val pullToRefreshState = rememberPullToRefreshState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black.copy(alpha = 0.6f)),
                title = {
                    Text(text = "My Tasks")
                },
                actions = {
                    Icon(
                        Icons.Default.MoreVert,
                        contentDescription = "Add",
                        modifier = Modifier.clickable(onClick = {
                            expanded = true
                        })
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        if (showAnalyticsFeature) {
                            DropdownMenuItem(
                                text = { Text("Open Analytics Page") },
                                onClick = {
                                    onOpenAnalyticsPageButtonClicked()
                                    expanded = false
                                }
                            )
                        }

                        DropdownMenuItem(
                            text = { Text("Logout") },
                            onClick = {
                                expanded = false
                                onAction(TasksListScreenAction.onLogoutClicked)
                            }
                        )
                    }
                }
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAction(TasksListScreenAction.OnAddNewTaskClicked) },
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        PullToRefreshBox(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            state = pullToRefreshState,
            isRefreshing = tasksListScreenState.isRefreshing,
            onRefresh = { onAction(TasksListScreenAction.OnPullToRefresh) }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 16.dp, top = 16.dp)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentPadding = PaddingValues(24.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
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

            }


        }
    }
}