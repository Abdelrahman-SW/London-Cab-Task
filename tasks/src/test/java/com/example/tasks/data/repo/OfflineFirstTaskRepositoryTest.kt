package com.example.tasks.data.repo

import androidx.room.Room
import com.example.core.domain.util.DataError
import com.example.core.domain.util.Result
import com.example.tasks.data.local.RoomLocalTaskDataSourceImpl
import com.example.tasks.data.local.db.TaskDb
import com.example.tasks.data.local.db.entities.TaskEntity
import com.example.tasks.data.local.mappers.toTask
import com.example.tasks.domain.datasources.LocalTasksDataSource
import com.example.tasks.domain.datasources.RemoteTasksDataSource
import com.example.tasks.domain.models.Task
import com.example.tasks.domain.repo.TaskRepository
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import com.google.common.truth.Truth.assertThat

class OfflineFirstTaskRepositoryTest {

    private lateinit var offlineFirstTaskRepository: TaskRepository
    private lateinit var localTasksDataSource: LocalTasksDataSource
    private lateinit var remoteTasksDataSource: RemoteTasksDataSource
    private lateinit var testCoroutineScope: CoroutineScope


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        localTasksDataSource = mockk(relaxed = true)
        remoteTasksDataSource = mockk(relaxed = true)
        testCoroutineScope = TestScope(UnconfinedTestDispatcher())
        offlineFirstTaskRepository = OfflineFirstTaskRepository(
            localTasksDataSource,
            remoteTasksDataSource,
            testCoroutineScope
        )
    }


    // test offline first logic
    @Test
    fun `get Tasks When User Is Offline will Return Tasks From Cache Local Database`() = runTest {
        // make api call return error as the user is offline
        coEvery { remoteTasksDataSource.getAllTasks() } returns Result.Error(DataError.Network.NO_INTERNET)
        val fakeTasks = listOf(
            TaskEntity(
                description = "task1",
                timeStamp = System.currentTimeMillis()
            ),
            TaskEntity(
                description = "task2",
                timeStamp = System.currentTimeMillis()
            ),
            TaskEntity(
                description = "task3",
                timeStamp = System.currentTimeMillis()
            ),
        )

        coEvery { localTasksDataSource.getAllTasks() } returns flowOf(fakeTasks.map { it.toTask() })

        val result = offlineFirstTaskRepository.getAllTasks().toList()

        assertThat(result[0].size).isEqualTo(3)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }
}