package com.jeongbj.data.auth.api

import com.jeongbj.core.common.BaseResponse
import com.jeongbj.data.auth.request.RefreshTokenRequest
import com.jeongbj.data.auth.response.AuthTokenResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/reissue")
    suspend fun refreshAccessToken(@Body refreshTokenRequest: RefreshTokenRequest): BaseResponse<AuthTokenResponse>

    @DELETE("auth/logout")
    suspend fun logout(): BaseResponse<Unit>

    @GET("auth/resign")
    suspend fun resign(): BaseResponse<Unit>
}