package com.jeongbj.domain.user.usecase

import com.jeongbj.core.common.ResultType
import com.jeongbj.domain.user.model.User
import kotlinx.coroutines.flow.Flow

interface GoogleLoginUseCase {
    operator fun invoke(idToken: String) : Flow<ResultType<User>>
}

interface KakaoLoginUseCase {
    operator fun invoke(idToken: String) : Flow<ResultType<User>>
}

interface AutoLoginUseCase {
    operator fun invoke(): Flow<ResultType<User>>
}