package com.jeongbj.data.user.usecase

import com.jeongbj.core.common.ResultType
import com.jeongbj.core.common.flowResult
import com.jeongbj.domain.user.repository.UserRepository
import com.jeongbj.domain.user.usecase.UpdateFcmTokenUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateFcmTokenUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
): UpdateFcmTokenUseCase {
    override fun invoke(enabled: Boolean): Flow<ResultType<Unit>> = flowResult {
        userRepository.updateFcmToken(enabled)
    }
}