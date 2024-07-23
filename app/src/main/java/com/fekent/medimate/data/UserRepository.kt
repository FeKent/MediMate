package com.fekent.medimate.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepository(private val dataStore: DataStore<Preferences>){
    private companion object{
        val USER_NAME = stringPreferencesKey("user_name")
        val IS_DARK_THEME = booleanPreferencesKey("is_dark_theme")
    }

    val currentUserName: Flow<String> =
        dataStore.data.map { preferences ->
            preferences[USER_NAME] ?: "User"
        }

    val isDarkTheme: Flow<Boolean> =
        dataStore.data.map { preferences ->
            preferences[IS_DARK_THEME] ?: false
        }

    suspend fun saveUserName(userName: String){
        dataStore.edit { preferences ->
            preferences[USER_NAME] = userName
        }
    }

    suspend fun saveThemePreference(isDark: Boolean){
        dataStore.edit { preferences ->
            preferences[IS_DARK_THEME] = isDark
        }
    }

}