package com.example.android.architecture.blueprints.todoapp.tasks

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.Event
import com.example.android.architecture.blueprints.todoapp.getOrAwaitValue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.google.common.truth.Truth.assertThat
import org.junit.Before

@RunWith(AndroidJUnit4::class)
class TasksViewModelTest {

    @get: Rule
    var instantTaskExecutorRule =  InstantTaskExecutorRule()


    // DO NOT initialize as val so that the same instance is not used in all tests.
    private lateinit var tasksViewModel: TasksViewModel

    @Before
    fun setupViewModel() {
        // GIVEN a fresh ViewModel
        tasksViewModel = TasksViewModel(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun addNewTask_setsNewTaskEvent() {
        // WHEN
        tasksViewModel.addNewTask()

        // THEN
        val result = tasksViewModel.newTaskEvent.getOrAwaitValue()
        assertThat(result.getContentIfNotHandled()).isNotEqualTo(null)
    }

    /*
    Old version of above. This observe LiveData manually without Util.
     */
    @Test
    fun addNewTask_setsNewTaskEvent_OLDVERSION() {
        val observer = Observer<Event<Unit>> {}

        try {
            // observe LiveData forever
            tasksViewModel.newTaskEvent.observeForever(observer)

            // WHEN adding a new task
            tasksViewModel.addNewTask()

            // THEN the new task event is triggered
            val result = tasksViewModel.newTaskEvent.value
            assertThat(result?.getContentIfNotHandled()).isNotEqualTo(null)

        } finally {
            tasksViewModel.newTaskEvent.removeObserver(observer)
        }
    }

    @Test
    fun setFilterAllTasks_tasksAddViewVisible() {
        // WHEN
        tasksViewModel.setFiltering(TasksFilterType.ALL_TASKS)

        // THEN
        val result = tasksViewModel.tasksAddViewVisible.getOrAwaitValue()
        assertThat(result).isTrue()
    }
}