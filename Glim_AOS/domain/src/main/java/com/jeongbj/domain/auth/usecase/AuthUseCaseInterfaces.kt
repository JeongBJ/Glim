package com.jeongbj.domain.auth.usecase

import com.jeongbj.core.common.ResultType
import com.jeongbj.domain.auth.model.AuthToken
import kotlinx.coroutines.flow.Flow

interface RefreshAccessTokenUseCase {
    operator fun invoke(): Flow<ResultType<AuthToken>>
}

interface ClearTokenUseCase {
    operator fun invoke(): Flow<ResultType<Unit>>
}

interface ResignUseCase {
    operator fun invoke(): Flow<ResultType<Unit>>
}

interface LogoutUseCase {
    operator fun invoke(): Flow<ResultType<Unit>>
}