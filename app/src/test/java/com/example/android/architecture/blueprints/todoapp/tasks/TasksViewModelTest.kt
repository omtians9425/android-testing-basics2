package com.example.android.architecture.blueprints.todoapp.tasks

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.Event
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.google.common.truth.Truth.assertThat

@RunWith(AndroidJUnit4::class)
class TasksViewModelTest {

    @get: Rule
    var instantTaskExecutorRule =  InstantTaskExecutorRule()

    @Test
    fun addNewTask_setsNewTaskEvent() {
        // GIVEN a fresh ViewModel
        val tasksViewModel = TasksViewModel(ApplicationProvider.getApplicationContext())

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
}