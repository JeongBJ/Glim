package com.jeongbj.domain.user.usecase

import com.jeongbj.core.common.ResultType
import com.jeongbj.domain.user.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface GetUserInfoUseCase {
    operator fun invoke(): Flow<ResultType<UserInfo>>
}