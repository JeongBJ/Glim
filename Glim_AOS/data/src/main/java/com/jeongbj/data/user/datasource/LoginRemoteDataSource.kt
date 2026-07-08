package com.jeongbj.data.user.datasource

import com.jeongbj.core.common.BaseResponse
import com.jeongbj.data.user.api.LoginApi
import com.jeongbj.data.user.request.LoginTokenRequest
import com.jeongbj.data.user.response.LoginResponse
import javax.inject.Inject

class LoginRemoteDataSource @Inject constructor(
    private val loginApi: LoginApi
) {

    suspend fun googleLogin(loginTokenRequest: LoginTokenRequest) : BaseResponse<LoginResponse> =
        loginApi.googleLogin(loginTokenRequest)

    suspend fun kakaoLogin(loginTokenRequest: LoginTokenRequest) : BaseResponse<LoginResponse> =
        loginApi.kakaoLogin(loginTokenRequest)

    suspend fun autoLogin(loginTokenRequest: LoginTokenRequest) : BaseResponse<LoginResponse> =
        loginApi.autoLogin(loginTokenRequest)
}