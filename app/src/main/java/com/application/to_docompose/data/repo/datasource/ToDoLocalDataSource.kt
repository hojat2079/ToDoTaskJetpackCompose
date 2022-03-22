package com.application.to_docompose.data.repo.datasource

import com.application.to_docompose.data.model.ToDoTask
import kotlinx.coroutines.flow.Flow

interface ToDoLocalDataSource {
    val getAllTask: Flow<List<ToDoTask>>

    val sortAllTaskByLowPriority: Flow<List<ToDoTask>>

    val sortAllTaskByHighPriority: Flow<List<ToDoTask>>

    fun getSelectedTask(taskId: Int): Flow<ToDoTask>

    suspend fun addTask(task: ToDoTask)

    suspend fun deleteTask(task: ToDoTask)

    suspend fun updateTask(task: ToDoTask)

    suspend fun deleteAllTask()

    fun searchDatabase(query: String): Flow<List<ToDoTask>>


}