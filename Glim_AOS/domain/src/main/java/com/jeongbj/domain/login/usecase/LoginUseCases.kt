package com.jeongbj.domain.login.usecase

import javax.inject.Inject

data class LoginUseCases @Inject constructor(
    val googleLoginUseCase: GoogleLoginUseCase,
    val kakaoLoginUseCase: KakaoLoginUseCase
)