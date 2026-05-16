package com.jeongbj.domain.user.usecase

import javax.inject.Inject

data class UserUseCases @Inject constructor(
    val updateProfileUseCase: UpdateProfileUseCase
)
