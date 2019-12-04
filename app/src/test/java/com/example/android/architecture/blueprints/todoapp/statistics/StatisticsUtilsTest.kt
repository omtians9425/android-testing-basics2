package com.example.android.architecture.blueprints.todoapp.statistics

import com.example.android.architecture.blueprints.todoapp.data.Task
import org.junit.Test
import com.google.common.truth.Truth.assertThat

class StatisticsUtilsTest {

    @Test
    fun getActiveAndCompletedStats_noCompleted_returnsHundredZero() {
        // GIVEN : create an active task
        val tasks = listOf(
                Task("title", "desc", isCompleted = false)
        )

        // WHEN : execute sut function
        val result = getActiveAndCompletedStats(tasks)

        // THEN : assert
        assertThat(result.activeTasksPercent).isEqualTo(100f)
        assertThat(result.completedTasksPercent).isEqualTo((0f))
    }

    @Test
    fun getActiveAndCompleteStats_noActive_returnsZeroHundred() {
        // GIVEN
        val tasks = listOf(
                Task("title", "desc", isCompleted = true)
        )

        // WHEN
        val result = getActiveAndCompletedStats(tasks)

        // THEN
        assertThat(result.activeTasksPercent).isEqualTo(0f)
        assertThat(result.completedTasksPercent).isEqualTo(100f)
    }

    @Test
    fun getActiveAndCompleteStats_threeActiveAndTwoCompleted_returnsSixtyForty() {
        // GIVEN
        val tasks = listOf(
                Task("title1", "desc", isCompleted = false),
                Task("title2", "desc", isCompleted = false),
                Task("title3", "desc", isCompleted = false),
                Task("title4", "desc", isCompleted = true),
                Task("title5", "desc", isCompleted = true)
        )

        // WHEN
        val result = getActiveAndCompletedStats(tasks)

        // THEN
        assertThat(result.activeTasksPercent).isEqualTo(60f)
        assertThat(result.completedTasksPercent).isEqualTo(40f)
    }

    @Test
    fun getActiveAndCompleteStats_empty_returnsZeros() {
        // GIVEN
        val tasks = emptyList<Task>()

        // WHEN
        val result = getActiveAndCompletedStats(tasks)

        // THEN
        assertThat(result.activeTasksPercent).isEqualTo(0f)
        assertThat(result.completedTasksPercent).isEqualTo(0f)
    }

    @Test
    fun getActiveAndCompleteStats_error_returnsZeros() {
        // GIVEN
        val tasks = null

        // WHEN
        val result = getActiveAndCompletedStats(tasks)

        // THEN
        assertThat(result.activeTasksPercent).isEqualTo(0f)
        assertThat(result.completedTasksPercent).isEqualTo(0f)
    }
}