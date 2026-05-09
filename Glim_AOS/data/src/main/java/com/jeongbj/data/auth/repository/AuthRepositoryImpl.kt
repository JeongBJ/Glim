package com.jeongbj.data.auth.repository

import com.jeongbj.core.common.unwrap
import com.jeongbj.data.auth.datasource.AuthRemoteDataSource
import com.jeongbj.data.auth.manager.TokenManager
import com.jeongbj.data.auth.mapper.toDomain
import com.jeongbj.data.auth.request.RefreshTokenRequest
import com.jeongbj.domain.auth.model.AuthToken
import com.jeongbj.domain.auth.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val tokenManager: TokenManager
) : AuthRepository {

    override suspend fun refreshAccessToken(refreshToken: String): AuthToken {
        return authRemoteDataSource.refreshAccessToken(RefreshTokenRequest(refreshToken))
            .unwrap()
            .toDomain()
    }

    override suspend fun clearToken() {
        return tokenManager.clearTokens()
    }

    override suspend fun resign() {
        return authRemoteDataSource.resign()
            .unwrap()
    }

    override suspend fun logout() {
        return tokenManager.clearTokens()
    }
}