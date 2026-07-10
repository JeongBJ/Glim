package com.jeongbj.data.user.repository

import com.jeongbj.core.common.MultipartImage
import com.jeongbj.core.common.unwrap
import com.jeongbj.data.fcm.manager.FcmTokenManager
import com.jeongbj.data.fcm.mapper.toRequest
import com.jeongbj.data.network.mapper.toJsonRequestBody
import com.jeongbj.data.network.mapper.toMultipartBody
import com.jeongbj.data.user.datasource.UserRemoteDataSource
import com.jeongbj.data.user.mapper.toDomain
import com.jeongbj.domain.user.model.User
import com.jeongbj.domain.user.repository.UserRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val fcmTokenManager: FcmTokenManager
) : UserRepository {
    override suspend fun updateProfile(
        image: MultipartImage?,
        profile: User
    ): User {
        return userRemoteDataSource.updateProfile(profile.toJsonRequestBody(), image.toMultipartBody())
            .unwrap()
            .toDomain()
    }

    override suspend fun updateFcmToken(enabled: Boolean) {
        val fcmToken = fcmTokenManager.getFcmToken().first() ?: return
        val newToken = fcmToken.copy(enabled = enabled)
        fcmTokenManager.saveFcmToken(fcmToken.token, enabled)
        return userRemoteDataSource.updateFcmToken(newToken.toRequest()).unwrap()
    }
}