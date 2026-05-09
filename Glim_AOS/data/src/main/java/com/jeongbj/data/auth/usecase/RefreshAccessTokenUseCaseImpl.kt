package com.jeongbj.data.auth.usecase

import com.jeongbj.core.common.ResultType
import com.jeongbj.core.common.flowResult
import com.jeongbj.data.auth.manager.TokenManager
import com.jeongbj.domain.auth.model.AuthToken
import com.jeongbj.domain.auth.repository.AuthRepository
import com.jeongbj.domain.auth.usecase.RefreshAccessTokenUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RefreshAccessTokenUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenManager: TokenManager
) : RefreshAccessTokenUseCase {

    override operator fun invoke(): Flow<ResultType<AuthToken>> {
        return flowResult {
            val newToken = authRepository.refreshAccessToken(refreshToken)
            tokenManager.saveAccessToken(newToken.accessToken)
            newToken
        }
    }
}