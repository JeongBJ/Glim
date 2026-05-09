package com.jeongbj.domain.login.usecase

import com.jeongbj.core.common.ResultType
import kotlinx.coroutines.flow.Flow

interface GoogleLoginUseCase {
    operator fun invoke(idToken: String, fcmToken: String = "") : Flow<ResultType<Unit>>
}

interface KakaoLoginUseCase {
    operator fun invoke(idToken: String, fcmToken: String = "") : Flow<ResultType<Unit>>
}