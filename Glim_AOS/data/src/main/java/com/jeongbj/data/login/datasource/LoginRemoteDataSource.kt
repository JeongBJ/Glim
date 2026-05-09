package com.jeongbj.data.login.datasource

import com.jeongbj.core.common.BaseResponse
import com.jeongbj.data.auth.response.AuthTokenResponse
import com.jeongbj.data.login.api.LoginApi
import com.jeongbj.data.login.request.LoginTokenRequest
import javax.inject.Inject

class LoginRemoteDataSource @Inject constructor(
    private val loginApi: LoginApi
) {

    suspend fun googleLogin(loginTokenRequest: LoginTokenRequest) : BaseResponse<AuthTokenResponse> =
        loginApi.googleLogin(loginTokenRequest)

    suspend fun kakaoLogin(loginTokenRequest: LoginTokenRequest) : BaseResponse<AuthTokenResponse> =
        loginApi.kakaoLogin(loginTokenRequest)
}