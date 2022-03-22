package com.application.to_docompose.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.application.to_docompose.util.DATABASE_TABLE

@Entity(tableName = DATABASE_TABLE)
data class ToDoTask(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val title: String,
    val desc: String,
    val priority: Priority
)