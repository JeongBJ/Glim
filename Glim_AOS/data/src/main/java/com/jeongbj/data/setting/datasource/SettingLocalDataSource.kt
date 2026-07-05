package com.jeongbj.data.setting.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import javax.inject.Inject

class SettingLocalDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    companion object {
        private val AUTO_LOGIN_ENABLED = booleanPreferencesKey("auto_login")
        private val LOCK_SCREEN_ENABLED = booleanPreferencesKey("lock_screen")
        private val PUSH_ENABLED = booleanPreferencesKey("push_enabled")
    }
}