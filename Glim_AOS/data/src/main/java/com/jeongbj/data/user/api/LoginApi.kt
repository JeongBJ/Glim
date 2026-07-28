package com.jeongbj.data.user.api

import com.jeongbj.core.common.BaseResponse
import com.jeongbj.data.user.request.LoginTokenRequest
import com.jeongbj.data.user.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("login/google")
    suspend fun googleLogin(@Body loginTokenRequest: LoginTokenRequest): BaseResponse<LoginResponse>

    @POST("login/kakao")
    suspend fun kakaoLogin(@Body loginTokenRequest: LoginTokenRequest): BaseResponse<LoginResponse>

    @POST("login")
    suspend fun autoLogin(@Body loginTokenRequest: LoginTokenRequest): BaseResponse<LoginResponse>

}