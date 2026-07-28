package com.jeongbj.data.setting.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.jeongbj.domain.setting.model.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingLocalDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    fun get(): Flow<Settings> =
        dataStore.data.map { preferences ->
            Settings(
                autoLoginEnabled = preferences[AUTO_LOGIN_ENABLED] ?: false,
                lockScreenEnabled = preferences[LOCK_SCREEN_ENABLED] ?: false,
                pushEnabled = preferences[PUSH_ENABLED] ?: false,
            )
        }

    suspend fun update(settings: Settings) {
        dataStore.edit { preferences ->
            preferences[AUTO_LOGIN_ENABLED] = settings.autoLoginEnabled
            preferences[LOCK_SCREEN_ENABLED] = settings.lockScreenEnabled
            preferences[PUSH_ENABLED] = settings.pushEnabled
        }
    }
    companion object {
        private val AUTO_LOGIN_ENABLED = booleanPreferencesKey("auto_login")
        private val LOCK_SCREEN_ENABLED = booleanPreferencesKey("lock_screen")
        private val PUSH_ENABLED = booleanPreferencesKey("push_enabled")
    }
}