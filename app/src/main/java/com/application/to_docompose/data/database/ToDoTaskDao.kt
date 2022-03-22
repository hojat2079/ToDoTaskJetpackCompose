package com.application.to_docompose.data.database

import androidx.room.*
import com.application.to_docompose.data.model.ToDoTask
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoTaskDao {
    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    fun getAllTask(): Flow<List<ToDoTask>>

    @Query("SELECT * FROM todo_table WHERE id=:taskId ")
    fun getSelectedTask(taskId: Int): Flow<ToDoTask>

    @Query("SELECT * FROM todo_table WHERE title LIKE :query OR `desc` LIKE :query")
    fun searchDatabase(query: String): Flow<List<ToDoTask>>

    @Delete
    suspend fun deleteTask(toDoTask: ToDoTask)

    @Query("DELETE FROM todo_table")
    suspend fun deleteAllTask()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(toDoTask: ToDoTask)

    @Update
    suspend fun updateTask(toDoTask: ToDoTask)

    @Query("SELECT * FROM TODO_TABLE ORDER BY CASE WHEN PRIORITY LIKE 'L%' THEN 1 WHEN PRIORITY LIKE 'M%' THEN 2 WHEN PRIORITY LIKE 'H%' THEN 3 END")
    fun sortTaskByLowPriority() :Flow<List<ToDoTask>>

    @Query("SELECT * FROM TODO_TABLE ORDER BY CASE WHEN PRIORITY LIKE 'H%' THEN 1 WHEN PRIORITY LIKE 'M%' THEN 2 WHEN PRIORITY LIKE 'L%' THEN 3 END")
    fun sortTaskByHighPriority(): Flow<List<ToDoTask>>

}