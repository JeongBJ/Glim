package com.jeongbj.data.fcm.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.jeongbj.data.fcm.mapper.toDomain
import com.jeongbj.data.fcm.request.FcmTokenRequest
import com.jeongbj.domain.user.model.FcmToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import javax.inject.Inject

class FcmTokenDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    fun get(): Flow<FcmToken?> {
        return dataStore.data.map { preferences ->
            preferences[PREF_KEY]?.let { json ->
                decodeFcmToken(json)
            }?.toDomain()
        }
    }

    suspend fun save(token: String, enabled: Boolean) {
        dataStore.edit { preferences ->
            val json = Json.encodeToString(FcmTokenRequest(token, enabled))
            preferences[PREF_KEY] = json
        }
    }

    suspend fun clear() {
        dataStore.edit {
            it.remove(PREF_KEY)
        }
    }

    private fun decodeFcmToken(json: String): FcmTokenRequest? {
        return runCatching {
            Json.decodeFromString<FcmTokenRequest>(json)
        }.getOrDefault(null)
    }

    companion object {
        private val PREF_KEY = stringPreferencesKey("fcm_token")
    }
}