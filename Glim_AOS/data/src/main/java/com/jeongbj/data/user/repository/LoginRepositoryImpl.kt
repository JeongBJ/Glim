package com.jeongbj.data.user.repository

import com.jeongbj.core.common.unwrap
import com.jeongbj.data.auth.manager.TokenManager
import com.jeongbj.data.auth.mapper.toDomain
import com.jeongbj.data.fcm.manager.FcmTokenManager
import com.jeongbj.data.fcm.mapper.toRequest
import com.jeongbj.data.user.datasource.LoginRemoteDataSource
import com.jeongbj.data.user.mapper.toDomain
import com.jeongbj.data.user.mapper.toRequest
import com.jeongbj.data.user.request.LoginTokenRequest
import com.jeongbj.domain.user.model.LoginToken
import com.jeongbj.domain.user.model.User
import com.jeongbj.domain.user.repository.LoginRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepositoryImpl @Inject constructor(
    private val loginRemoteDataSource: LoginRemoteDataSource,
    private val tokenManager: TokenManager,
    private val fcmTokenManager: FcmTokenManager,
) : LoginRepository {
    override suspend fun googleLogin(loginToken: LoginToken): User {
        val fcmToken = fcmTokenManager.getFcmToken().first()
        val loginRequest = loginToken.toRequest(fcmToken)
        val response = loginRemoteDataSource.googleLogin(loginRequest).unwrap()
        tokenManager.saveTokens(response.token.toDomain())
        return response.user.toDomain()
    }

    override suspend fun kakaoLogin(loginToken: LoginToken): User {
        val fcmToken = fcmTokenManager.getFcmToken().first()
        val loginRequest = loginToken.toRequest(fcmToken)
        val response = loginRemoteDataSource.kakaoLogin(loginRequest).unwrap()
        tokenManager.saveTokens(response.token.toDomain())
        return response.user.toDomain()
    }

    override suspend fun autoLogin(): User {
        val refreshToken = tokenManager.getRefreshToken() ?: throw Exception("Refresh Token is Missing")
        val fcmToken = fcmTokenManager.getFcmToken().first()
        val response = loginRemoteDataSource.autoLogin(LoginTokenRequest(
            idToken = refreshToken,
            fcmTokenRequest = fcmToken?.toRequest()
        )).unwrap()
        tokenManager.saveTokens(response.token.toDomain())
        return response.user.toDomain()
    }
}