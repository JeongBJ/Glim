package com.jeongbj.data.auth.usecase

import com.jeongbj.core.common.ResultType
import com.jeongbj.domain.auth.repository.AuthRepository
import com.jeongbj.domain.auth.usecase.LogoutUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LogoutUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : LogoutUseCase {
    override fun invoke(): Flow<ResultType<Unit>> = authRepository.logout()

}