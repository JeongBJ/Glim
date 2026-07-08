package com.jeongbj.data.fcm.manager

import com.jeongbj.data.fcm.datastore.FcmTokenDataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FcmTokenManager @Inject constructor(
    private val fcmTokenDataStore: FcmTokenDataStore
) {

    suspend fun clearFcmToken() {
        fcmTokenDataStore.clear()
    }

    suspend fun saveFcmToken(token: String) {
        fcmTokenDataStore.save(token)
    }

    fun getFcmToken(): Flow<String> {
        return fcmTokenDataStore.get()
    }

}