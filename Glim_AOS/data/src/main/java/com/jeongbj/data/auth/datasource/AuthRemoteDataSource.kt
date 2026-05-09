package com.jeongbj.data.auth.datasource

import com.jeongbj.core.common.BaseResponse
import com.jeongbj.data.auth.api.AuthApi
import com.jeongbj.data.auth.request.RefreshTokenRequest
import com.jeongbj.data.auth.response.AuthTokenResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRemoteDataSource @Inject constructor(
    private val authApi: AuthApi,
){
    suspend fun refreshAccessToken(refreshTokenRequest: RefreshTokenRequest)
    : BaseResponse<AuthTokenResponse> =
        authApi.refreshAccessToken(refreshTokenRequest)
    

    suspend fun clearToken(): BaseResponse<Unit> =
        authApi.logout()


    suspend fun resign(): BaseResponse<Unit> =
        authApi.resign()

}