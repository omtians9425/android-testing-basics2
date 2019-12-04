package com.example.android.architecture.blueprints.todoapp.statistics

import com.example.android.architecture.blueprints.todoapp.data.Task
import org.junit.Test
import com.google.common.truth.Truth.assertThat

class StatisticsUtilsTest {

    @Test
    fun getActiveAndCompletedStats_noCompleted_returnsHundredZero() {
        // GIVEN : create an active task
        val tasks = listOf<Task>(
                Task("title", "desc", isCompleted = false)
        )

        // WHEN : execute sut function
        val result = getActiveAndCompletedStats(tasks)

        // THEN : assert
        assertThat(result.activeTasksPercent).isEqualTo(100f)
        assertThat(result.completedTasksPercent).isEqualTo((0f))
    }
}