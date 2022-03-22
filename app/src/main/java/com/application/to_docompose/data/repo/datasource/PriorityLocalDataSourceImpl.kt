package com.application.to_docompose.data.repo.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.application.to_docompose.data.model.Priority
import com.application.to_docompose.util.PREFERENCES_PRIORITY_STATE_KEY
import com.application.to_docompose.util.PREFERENCES_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(PREFERENCES_NAME)

@Singleton
class PriorityLocalDataSourceImpl @Inject constructor(
    @ApplicationContext context: Context
) : PriorityLocalDataSource {

    private val dataStore = context.dataStore

    private object PreferencesKey {
        val sortState = stringPreferencesKey(PREFERENCES_PRIORITY_STATE_KEY)
    }

    override val readSortState: Flow<String>
        get() = dataStore.data
            .catch { exception ->
                if (exception is IOException)
                    emit(emptyPreferences())
                else throw exception
            }.map {
                val sortState = it[PreferencesKey.sortState] ?: Priority.NONE.name
                sortState
            }

    override suspend fun editSortState(priority: Priority) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.sortState] = priority.name
        }
    }
}