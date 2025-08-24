package com.example.tasks

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.core.presentation.designsystem.LondonCapTaskTheme
import com.example.tasks.domain.models.Task
import com.example.tasks.presentation.tasks_list.TasksListScreenRoot
import com.example.tasks.presentation.tasks_list.TasksListScreenState
import com.example.tasks.presentation.tasks_list.TasksListViewModel
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.junit.After
import org.junit.Rule
import org.junit.Test
import kotlin.collections.listOf

class TaskListScreenTest {
    @get:Rule
    val composeRule = createComposeRule()


    @Test
    fun testTaskListScreenDisplaysTasks() {
        // Given some tasks in the mock ViewModel
        composeRule.setContent {
            LondonCapTaskTheme {
                TasksListScreenRoot(
                    onOpenAnalyticsPageButtonClicked = {},
                    onNavigateToUpsertTask = {},
                    taskListViewModel = mockViewModel(
                        tasks = listOf(
                            Task(
                                description = "task1",
                                timeStamp = System.currentTimeMillis()
                            ),
                            Task(
                                description = "task2",
                                timeStamp = System.currentTimeMillis()
                            )
                        )
                    ),
                    afterLogout = {}
                )
            }
        }
        // Then verify the tasks are displayed
        composeRule.onNodeWithText("task1").assertIsDisplayed()
        composeRule.onNodeWithText("task2").assertIsDisplayed()
    }


    @After
    fun tearDown() {
        clearAllMocks()
    }

    fun mockViewModel(tasks: List<Task> = emptyList()): TasksListViewModel {
        return mockk(relaxed = true) {
            every { state } returns TasksListScreenState(
                tasks = tasks,
                isRefreshing = false
            )
        }
    }

}