package com.jeongbj.data.user.usecase

import com.jeongbj.core.common.ResultType
import com.jeongbj.core.common.flowResult
import com.jeongbj.domain.user.model.LoginToken
import com.jeongbj.domain.user.model.User
import com.jeongbj.domain.user.repository.LoginRepository
import com.jeongbj.domain.user.usecase.KakaoLoginUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KakaoLoginUseCaseImpl @Inject constructor(
    private val loginRepository: LoginRepository
) : KakaoLoginUseCase {
    override operator fun invoke(idToken: String): Flow<ResultType<User>> = flowResult {
        loginRepository.kakaoLogin(LoginToken(idToken))
    }
}