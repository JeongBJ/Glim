package com.jeongbj.data.user.usecase

import com.jeongbj.core.common.ResultType
import com.jeongbj.core.common.flowResult
import com.jeongbj.domain.user.model.User
import com.jeongbj.domain.user.repository.LoginRepository
import com.jeongbj.domain.user.usecase.AutoLoginUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AutoLoginUseCaseImpl @Inject constructor(
    private val loginRepository: LoginRepository
) : AutoLoginUseCase {
    override fun invoke(): Flow<ResultType<User>> = flowResult {
        loginRepository.autoLogin()
    }
}