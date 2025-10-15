package com.example.movieaap.Data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class ThemeDataStore(context: Context) {

    private val dataStore = context.dataStore


    companion object {
        val IS_DARK_MODE_KEY = booleanPreferencesKey("is_dark_mode")
    }


    suspend fun saveThemePreference(isDarkMode: Boolean) {
        dataStore.edit { settings ->
            settings[IS_DARK_MODE_KEY] = isDarkMode
        }
    }


    val getThemePreference: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[IS_DARK_MODE_KEY] ?: false
    }
}