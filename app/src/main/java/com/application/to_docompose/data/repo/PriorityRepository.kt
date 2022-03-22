package com.application.to_docompose.data.repo

import com.application.to_docompose.data.model.Priority
import kotlinx.coroutines.flow.Flow

interface PriorityRepository {
    val readSortState: Flow<String>

    suspend fun editSortState(priority: Priority)
}