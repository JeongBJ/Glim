package com.jeongbj.data.user.usecase

import com.jeongbj.core.common.MultipartImage
import com.jeongbj.core.common.ResultType
import com.jeongbj.core.common.flowResult
import com.jeongbj.domain.user.model.User
import com.jeongbj.domain.user.repository.UserRepository
import com.jeongbj.domain.user.usecase.UpdateProfileUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateProfileUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : UpdateProfileUseCase {
    override operator fun invoke(
        image: MultipartImage,
        profile: User
    ): Flow<ResultType<User>> = flowResult {
        userRepository.updateProfile(image, profile)
    }
}