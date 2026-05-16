package com.jeongbj.data.user.usecase

import com.jeongbj.core.common.ResultType
import com.jeongbj.core.common.flowResult
import com.jeongbj.domain.user.model.LoginToken
import com.jeongbj.domain.user.model.User
import com.jeongbj.domain.user.repository.LoginRepository
import com.jeongbj.domain.user.usecase.GoogleLoginUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GoogleLoginUseCaseImpl @Inject constructor(
    private val loginRepository: LoginRepository
) : GoogleLoginUseCase {
    override operator fun invoke(idToken: String): Flow<ResultType<User>> = flowResult {
        loginRepository.googleLogin(LoginToken(idToken))
    }
}