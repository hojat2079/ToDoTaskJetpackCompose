package com.application.to_docompose.data.repo

import com.application.to_docompose.data.model.ToDoTask
import com.application.to_docompose.data.repo.datasource.ToDoLocalDataSource
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class ToDoRepositoryImpl @Inject constructor(
    private val localDataSource: ToDoLocalDataSource
) : ToDoRepository {

    override val getAllTask: Flow<List<ToDoTask>>
        get() = localDataSource.getAllTask
    override val sortAllTaskByLowPriority: Flow<List<ToDoTask>>
        get() = localDataSource.sortAllTaskByLowPriority
    override val sortAllTaskByHighPriority: Flow<List<ToDoTask>>
        get() = localDataSource.sortAllTaskByHighPriority

    override fun getSelectedTask(taskId: Int): Flow<ToDoTask> {
        return localDataSource.getSelectedTask(taskId)
    }

    override suspend fun addTask(task: ToDoTask) {
        return localDataSource.addTask(task)
    }

    override suspend fun deleteTask(task: ToDoTask) {
        return localDataSource.deleteTask(task)
    }

    override suspend fun updateTask(task: ToDoTask) {
        return localDataSource.updateTask(task)
    }

    override suspend fun deleteAllTask() {
        return localDataSource.deleteAllTask()
    }

    override fun searchDatabase(query: String): Flow<List<ToDoTask>> {
        return localDataSource.searchDatabase(query)
    }
}