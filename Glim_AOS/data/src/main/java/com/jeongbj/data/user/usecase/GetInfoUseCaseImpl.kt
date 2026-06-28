package com.jeongbj.data.user.usecase

import com.jeongbj.core.common.ResultType
import com.jeongbj.core.common.flowResult
import com.jeongbj.domain.user.model.UserInfo
import com.jeongbj.domain.user.repository.UserRepository
import com.jeongbj.domain.user.usecase.GetUserInfoUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetInfoUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
): GetUserInfoUseCase {
    override fun invoke(): Flow<ResultType<UserInfo>> = flowResult {
        userRepository.getUserInfo()
    }
}