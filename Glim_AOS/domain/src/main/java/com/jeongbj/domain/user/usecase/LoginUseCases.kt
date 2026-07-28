package com.jeongbj.domain.user.usecase

import javax.inject.Inject

data class LoginUseCases @Inject constructor(
    val googleLoginUseCase: GoogleLoginUseCase,
    val kakaoLoginUseCase: KakaoLoginUseCase,
    val autoLoginUseCase: AutoLoginUseCase
)