package com.jeongbj.data.auth.repository

import com.jeongbj.core.common.ResultType
import com.jeongbj.core.common.mapBaseResponse
import com.jeongbj.data.auth.datasource.AuthRemoteDataSource
import com.jeongbj.data.auth.manager.TokenManager
import com.jeongbj.data.auth.mapper.toDomain
import com.jeongbj.data.auth.request.RefreshTokenRequest
import com.jeongbj.domain.auth.model.AuthToken
import com.jeongbj.domain.auth.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val tokenManager: TokenManager
) : AuthRepository {

    override fun refreshAccessToken(): Flow<ResultType<AuthToken>> = flow {
        val refreshToken = tokenManager.getRefreshToken()
        if (refreshToken.isNullOrBlank()) {
            throw IllegalStateException("RefreshToken is Missing")
        }
        emit(authRemoteDataSource.refreshAccessToken(RefreshTokenRequest(refreshToken)))
    }.mapBaseResponse { response ->
        val token = response ?: throw IllegalStateException("Response data is null")
        val accessToken = token.accessToken ?: throw IllegalStateException("AccessToken is null")
        tokenManager.saveAccessToken(accessToken)
        token.toDomain()
    }

    override fun clearToken(): Flow<ResultType<Unit>> = flow {
        tokenManager.clearTokens()
        emit(ResultType.Success(Unit))
    }

    override fun resign(): Flow<ResultType<Unit>> = flow {
        emit(authRemoteDataSource.resign())
    }.mapBaseResponse {
        tokenManager.clearTokens()
    }

    override fun logout(): Flow<ResultType<Unit>> = flow {
        tokenManager.clearTokens()
        emit(ResultType.Success(Unit))
    }
}