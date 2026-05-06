package com.jeongbj.domain.auth.usecase

import com.jeongbj.core.ResultType
import com.jeongbj.domain.auth.model.AuthToken
import kotlinx.coroutines.flow.Flow

interface RefreshAccessTokenUseCase {
    suspend operator fun invoke(): Flow<ResultType<AuthToken>>
}

interface ClearTokenUseCase {
    suspend operator fun invoke(): Flow<ResultType<Unit>>
}

interface ResignUseCase {
    suspend operator fun invoke(): Flow<ResultType<Unit>>
}
