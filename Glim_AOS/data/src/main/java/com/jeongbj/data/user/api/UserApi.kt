package com.jeongbj.data.user.api

import com.jeongbj.core.common.BaseResponse
import com.jeongbj.data.fcm.request.FcmTokenRequest
import com.jeongbj.data.user.response.UserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part

interface UserApi {
    @Multipart
    @PATCH("user/profile")
    suspend fun updateProfile(
        @Part("request") profile: RequestBody,
        @Part image: MultipartBody.Part?
    ): BaseResponse<UserResponse>

    @POST("user/fcm")
    suspend fun updateFcmToken(@Body fcmTokenRequest: FcmTokenRequest): BaseResponse<Unit>
}