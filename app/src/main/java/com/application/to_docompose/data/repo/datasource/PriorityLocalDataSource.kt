package com.application.to_docompose.data.repo.datasource

import com.application.to_docompose.data.model.Priority
import kotlinx.coroutines.flow.Flow

interface PriorityLocalDataSource {
    val readSortState: Flow<String>

    suspend fun editSortState(priority: Priority)
}