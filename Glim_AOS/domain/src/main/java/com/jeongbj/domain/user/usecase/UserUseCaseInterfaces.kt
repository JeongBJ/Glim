package com.jeongbj.domain.user.usecase

import com.jeongbj.core.common.MultipartImage
import com.jeongbj.core.common.ResultType
import com.jeongbj.domain.user.model.User
import kotlinx.coroutines.flow.Flow

interface UpdateProfileUseCase {
    operator fun invoke(image: MultipartImage?, profile: User): Flow<ResultType<User>>
}

interface UpdateFcmTokenUseCase {
    operator fun invoke(enabled: Boolean): Flow<ResultType<Unit>>
}