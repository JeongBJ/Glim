package com.jeongbj.data.login.usecase

import com.jeongbj.core.common.ResultType
import com.jeongbj.core.common.flowResult
import com.jeongbj.data.auth.manager.TokenManager
import com.jeongbj.domain.login.model.LoginToken
import com.jeongbj.domain.login.repository.LoginRepository
import com.jeongbj.domain.login.usecase.GoogleLoginUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GoogleLoginUseCaseImpl @Inject constructor(
    private val loginRepository: LoginRepository,
    private val tokenManager: TokenManager
) : GoogleLoginUseCase {
    override fun invoke(idToken: String, fcmToken: String): Flow<ResultType<Unit>> = flowResult {
        val tokens = loginRepository.googleLogin(LoginToken(idToken, fcmToken))
        tokenManager.saveTokens(tokens)
    }
}