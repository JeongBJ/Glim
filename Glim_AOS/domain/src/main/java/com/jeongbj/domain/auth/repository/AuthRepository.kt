package com.jeongbj.domain.auth.repository

import com.jeongbj.core.ResultType
import com.jeongbj.domain.auth.model.AuthToken
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun refreshAccessToken(): Flow<ResultType<AuthToken>>

    suspend fun clearToken(): Flow<ResultType<Unit>>

    suspend fun resign(): Flow<ResultType<Unit>>
}