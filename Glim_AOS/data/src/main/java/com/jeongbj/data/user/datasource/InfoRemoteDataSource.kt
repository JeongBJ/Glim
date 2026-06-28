package com.jeongbj.data.user.datasource

import com.jeongbj.core.common.BaseResponse
import com.jeongbj.data.user.api.InfoApi
import com.jeongbj.data.user.response.UserInfoResponse
import javax.inject.Inject

class InfoRemoteDataSource @Inject constructor(
    private val infoApi: InfoApi
) {
    suspend fun getInfo(): BaseResponse<UserInfoResponse> =
        infoApi.getInfo()
}