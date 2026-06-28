package com.jeongbj.data.user.api

import com.jeongbj.core.common.BaseResponse
import com.jeongbj.data.user.response.UserInfoResponse
import retrofit2.http.GET

interface InfoApi {
    @GET("info")
    suspend fun getInfo(): BaseResponse<UserInfoResponse>
}