package com.example.android.architecture.blueprints.todoapp

import android.content.Context
import androidx.room.Room
import com.example.android.architecture.blueprints.todoapp.data.source.DefaultTasksRepository
import com.example.android.architecture.blueprints.todoapp.data.source.TasksDataSource
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository
import com.example.android.architecture.blueprints.todoapp.data.source.local.TasksLocalDataSource
import com.example.android.architecture.blueprints.todoapp.data.source.local.ToDoDatabase
import com.example.android.architecture.blueprints.todoapp.data.source.remote.TasksRemoteDataSource

object ServiceLocator {
    private var database: ToDoDatabase? = null

    @Volatile
    var tasksRepository: TasksRepository? = null

    fun provideTasksRepository(ctx: Context): TasksRepository {
        synchronized(this) {
            return tasksRepository ?: createTasksRepository(ctx)
        }
    }

    private fun createTasksRepository(ctx: Context): TasksRepository {
        val newRepo = DefaultTasksRepository(
                tasksRemoteDataSource = TasksRemoteDataSource,
                tasksLocalDataSource = createTasksLocalDataSource(ctx))
        tasksRepository = newRepo
        return newRepo
    }

    private fun createTasksLocalDataSource(ctx: Context): TasksDataSource {
        val database = database ?: createDatabase(ctx)
        return TasksLocalDataSource(database.taskDao())
    }

    private fun createDatabase(ctx: Context): ToDoDatabase {
        val newDb = Room.databaseBuilder(ctx, ToDoDatabase::class.java, "Tasks.db").build()
        database = newDb
        return newDb
    }
}