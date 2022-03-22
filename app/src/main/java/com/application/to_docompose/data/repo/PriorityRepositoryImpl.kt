package com.application.to_docompose.data.repo

import com.application.to_docompose.data.model.Priority
import com.application.to_docompose.data.repo.datasource.PriorityLocalDataSource
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class PriorityRepositoryImpl @Inject constructor(
    private val localDataSource: PriorityLocalDataSource
) : PriorityRepository {
    override val readSortState: Flow<String>
        get() = localDataSource.readSortState

    override suspend fun editSortState(priority: Priority) {
        localDataSource.editSortState(priority = priority)
    }

}