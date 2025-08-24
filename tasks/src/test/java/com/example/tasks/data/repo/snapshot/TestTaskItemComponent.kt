package com.example.tasks.data.repo.snapshot

import app.cash.paparazzi.Paparazzi
import org.junit.Rule
import org.junit.Test
import com.example.tasks.domain.models.Task
import com.example.tasks.presentation.tasks_list.components.TaskItem

class TestTaskItemComponent {

    @get:Rule
    val paparazzi = Paparazzi()


    // first time : ./gradlew recordPaparazziDebug
    // to verify : ./gradlew verifyPaparazziDebug

    @Test
    fun testMyText() {
        paparazzi.snapshot {
            TaskItem(
                task = Task(
                    description = "Task1",
                    timeStamp = System.currentTimeMillis()
                ),
                onTaskClicked = {}
            )
        }
    }
}