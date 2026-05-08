package com.jeongbj.domain.auth.repository

import com.jeongbj.core.common.ResultType
import com.jeongbj.domain.auth.model.AuthToken
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun refreshAccessToken(): Flow<ResultType<AuthToken>>

    fun clearToken(): Flow<ResultType<Unit>>

    fun resign(): Flow<ResultType<Unit>>

    fun logout(): Flow<ResultType<Unit>>
}