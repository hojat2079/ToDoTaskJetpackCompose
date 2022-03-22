package com.application.to_docompose.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.application.to_docompose.util.DATABASE_VERSION
import com.application.to_docompose.data.model.ToDoTask

@Database(entities = [ToDoTask::class], version = DATABASE_VERSION, exportSchema = false)
abstract class ToDoTaskDatabase : RoomDatabase() {
    abstract fun getDao(): ToDoTaskDao
}