package com.application.to_docompose.di

import com.application.to_docompose.data.repo.PriorityRepository
import com.application.to_docompose.data.repo.PriorityRepositoryImpl
import com.application.to_docompose.data.repo.ToDoRepository
import com.application.to_docompose.data.repo.ToDoRepositoryImpl
import com.application.to_docompose.data.repo.datasource.PriorityLocalDataSource
import com.application.to_docompose.data.repo.datasource.PriorityLocalDataSourceImpl
import com.application.to_docompose.data.repo.datasource.ToDoLocalDataSource
import com.application.to_docompose.data.repo.datasource.ToDoLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface BindModule {
    @Binds
    fun provideToDoRepositoryImpl(toDoRepositoryImpl: ToDoRepositoryImpl): ToDoRepository

    @Binds
    fun provideToDoLocalDataSourceImpl(localDataSourceImpl: ToDoLocalDataSourceImpl): ToDoLocalDataSource

    @Binds
    fun providePriorityRepositoryImpl(toDoRepositoryImpl: PriorityRepositoryImpl): PriorityRepository

    @Binds
    fun providePriorityLocalDataSourceImpl(localDataSourceImpl: PriorityLocalDataSourceImpl): PriorityLocalDataSource

}