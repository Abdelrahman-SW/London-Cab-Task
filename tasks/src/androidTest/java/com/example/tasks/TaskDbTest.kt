package com.example.tasks

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.example.core.domain.util.Result
import com.example.tasks.data.local.RoomLocalTaskDataSourceImpl
import com.example.tasks.data.local.db.TaskDb
import com.example.tasks.data.local.db.entities.TaskEntity
import com.example.tasks.data.local.mappers.toTask
import com.example.tasks.data.repo.OfflineFirstTaskRepository
import com.example.tasks.domain.datasources.LocalTasksDataSource
import com.example.tasks.domain.datasources.RemoteTasksDataSource
import com.example.tasks.domain.models.Task
import com.example.tasks.domain.repo.TaskRepository
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import com.google.common.truth.Truth.assertThat

class TaskDbTest {

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


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getUpdatedTasksFromServer_UpdatesTheCacheOutdatedData() = runTest {

        val testDb = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            TaskDb::class.java
        ).build()

        val dao  = testDb.taskDao()

        localTasksDataSource = RoomLocalTaskDataSourceImpl(dao)

        offlineFirstTaskRepository = OfflineFirstTaskRepository(
            localTasksDataSource,
            remoteTasksDataSource,
            CoroutineScope(UnconfinedTestDispatcher())
        )

        val oldCachedTasks = listOf(
            TaskEntity(
                description = "Old1",
                timeStamp = System.currentTimeMillis()
            ),
            TaskEntity(
                description = "Old2",
                timeStamp = System.currentTimeMillis()
            ),
            TaskEntity(
                description = "Old3",
                timeStamp = System.currentTimeMillis()
            ),
        )

        dao.upsertTasks(oldCachedTasks)

        val fakeRemoteTasks = listOf(
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

        coEvery { remoteTasksDataSource.getAllTasks() } returns Result.Success<List<Task>>(data = fakeRemoteTasks.map { it.toTask() })

        offlineFirstTaskRepository.fetchTasks()

        val dbTasks = dao.getAllTasks().toList()

        assertThat(dbTasks[0][0].description).isEqualTo("task1")
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }
}