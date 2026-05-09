package com.jeongbj.data.login.api

import com.jeongbj.core.common.BaseResponse
import com.jeongbj.data.auth.response.AuthTokenResponse
import com.jeongbj.data.login.request.LoginTokenRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("login/google")
    suspend fun googleLogin(@Body loginTokenRequest: LoginTokenRequest): BaseResponse<AuthTokenResponse>

    @POST("login/google")
    suspend fun kakaoLogin(@Body loginTokenRequest: LoginTokenRequest): BaseResponse<AuthTokenResponse>

}