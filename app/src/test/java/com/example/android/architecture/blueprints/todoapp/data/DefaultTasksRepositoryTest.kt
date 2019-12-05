package com.example.android.architecture.blueprints.todoapp.data

import com.example.android.architecture.blueprints.todoapp.data.source.DefaultTasksRepository
import com.example.android.architecture.blueprints.todoapp.data.source.FakeDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import com.google.common.truth.Truth.assertThat

@ExperimentalCoroutinesApi
class DefaultTasksRepositoryTest {
    private val task1 = Task("Title1", "Description1")
    private val task2 = Task("Title2", "Description2")
    private val task3 = Task("Title3", "Description3")
    private val remoteTasks = listOf(task1, task2).sortedBy { it.id }
    private val localTasks = listOf(task3).sortedBy { it.id }
    private val newTasks = listOf(task3).sortedBy { it.id }

    private lateinit var tasksRemoteDataSource: FakeDataSource
    private lateinit var tasksLocalDataSource: FakeDataSource

    // Class under the test
    private lateinit var tasksRepository: DefaultTasksRepository

    @Before
    fun createRepository() {
        tasksLocalDataSource = FakeDataSource(localTasks.toMutableList())
        tasksRemoteDataSource = FakeDataSource(remoteTasks.toMutableList())

        tasksRepository = DefaultTasksRepository(
                tasksRemoteDataSource,
                tasksLocalDataSource,
                Dispatchers.Unconfined
        )
    }

    @Test
    fun getTasks_requestsAllTasksFromRemoteDataSource() = runBlockingTest {
        val result = tasksRepository.getTasks(forceUpdate = true) as Result.Success
        assertThat(result.data).isEqualTo(remoteTasks)
    }
}