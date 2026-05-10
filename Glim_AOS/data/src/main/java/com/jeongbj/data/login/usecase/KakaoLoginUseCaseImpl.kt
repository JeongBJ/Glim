package com.jeongbj.data.login.usecase

import com.jeongbj.core.common.ResultType
import com.jeongbj.core.common.flowResult
import com.jeongbj.data.auth.manager.TokenManager
import com.jeongbj.domain.login.model.LoginToken
import com.jeongbj.domain.login.repository.LoginRepository
import com.jeongbj.domain.login.usecase.KakaoLoginUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KakaoLoginUseCaseImpl @Inject constructor(
    private val loginRepository: LoginRepository,
    private val tokenManager: TokenManager
) : KakaoLoginUseCase {
    override fun invoke(idToken: String): Flow<ResultType<Unit>> = flowResult {
        val tokens = loginRepository.kakaoLogin(LoginToken(idToken))
        tokenManager.saveTokens(tokens)
    }
}