package com.jeongbj.data.auth.usecase

import com.jeongbj.core.common.ResultType
import com.jeongbj.domain.auth.repository.AuthRepository
import com.jeongbj.domain.auth.usecase.ClearTokenUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClearTokenUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : ClearTokenUseCase {
    override fun invoke(): Flow<ResultType<Unit>> = authRepository.clearToken()
}