package com.jeongbj.domain.auth.usecase

import javax.inject.Inject

data class AuthUseCases @Inject constructor(
    val refreshAccessTokenUseCase: RefreshAccessTokenUseCase,
    val clearTokenUseCase: ClearTokenUseCase,
    val resignUseCase: ResignUseCase,
    val logoutUseCase: LogoutUseCase
)