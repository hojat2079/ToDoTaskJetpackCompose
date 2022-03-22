package com.application.to_docompose.data.repo.datasource

import com.application.to_docompose.data.database.ToDoTaskDao
import com.application.to_docompose.data.model.ToDoTask
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ToDoLocalDataSourceImpl @Inject constructor(
    private val toDoTaskDao: ToDoTaskDao
) : ToDoLocalDataSource {

    override val getAllTask: Flow<List<ToDoTask>>
        get() = toDoTaskDao.getAllTask()
    override val sortAllTaskByLowPriority: Flow<List<ToDoTask>>
        get() = toDoTaskDao.sortTaskByLowPriority()
    override val sortAllTaskByHighPriority: Flow<List<ToDoTask>>
        get() = toDoTaskDao.sortTaskByHighPriority()

    override fun getSelectedTask(taskId: Int): Flow<ToDoTask> {
        return toDoTaskDao.getSelectedTask(taskId)
    }

    override suspend fun addTask(task: ToDoTask) {
        return toDoTaskDao.insertTask(task)
    }

    override suspend fun deleteTask(task: ToDoTask) {
        return toDoTaskDao.deleteTask(task)
    }

    override suspend fun updateTask(task: ToDoTask) {
        return toDoTaskDao.updateTask(task)
    }

    override suspend fun deleteAllTask() {
        return toDoTaskDao.deleteAllTask()
    }

    override fun searchDatabase(query: String): Flow<List<ToDoTask>> {
        return toDoTaskDao.searchDatabase(query = query)
    }
}