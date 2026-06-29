package com.jeongbj.data.user.usecase.info

import com.jeongbj.core.common.ResultType
import com.jeongbj.core.common.flowResult
import com.jeongbj.domain.user.model.UserInfo
import com.jeongbj.domain.user.repository.InfoRepository
import com.jeongbj.domain.user.usecase.GetUserInfoUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserInfoUseCaseImpl @Inject constructor(
    private val infoRepository: InfoRepository
): GetUserInfoUseCase {
    override fun invoke(): Flow<ResultType<UserInfo>> = flowResult {
        infoRepository.getUserInfo()
    }
}