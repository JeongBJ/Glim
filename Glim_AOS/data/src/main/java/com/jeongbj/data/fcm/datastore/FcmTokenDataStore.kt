package com.jeongbj.data.fcm.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FcmTokenDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    fun get(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[PREF_KEY] ?: ""
        }
    }

    suspend fun save(token: String) {
        dataStore.edit { preferences ->
            preferences[PREF_KEY] = token
        }
    }

    suspend fun clear() {
        dataStore.edit {
            it.remove(PREF_KEY)
        }
    }

    companion object {
        private val PREF_KEY = stringPreferencesKey("fcm_token")
    }
}