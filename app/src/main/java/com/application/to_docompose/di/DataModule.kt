package com.application.to_docompose.di

import android.content.Context
import androidx.room.Room
import com.application.to_docompose.util.DATABASE_NAME
import com.application.to_docompose.data.database.ToDoTaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Singleton
    @Provides
    fun createInstanceRoomDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, ToDoTaskDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideDao(database: ToDoTaskDatabase) = database.getDao()

}