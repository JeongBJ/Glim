package com.jeongbj.data.fcm.manager

import com.jeongbj.data.fcm.datastore.FcmTokenDataStore
import com.jeongbj.domain.user.model.FcmToken
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

    suspend fun saveFcmToken(token: String, enabled: Boolean = true) {
        fcmTokenDataStore.save(token, enabled)
    }

    fun getFcmToken(): Flow<FcmToken?> {
        return fcmTokenDataStore.get()
    }

}