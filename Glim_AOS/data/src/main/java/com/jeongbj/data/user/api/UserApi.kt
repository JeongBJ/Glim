package com.jeongbj.data.user.api

import com.jeongbj.core.common.BaseResponse
import com.jeongbj.data.user.response.UserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.PATCH
import retrofit2.http.Part

interface UserApi {
    @PATCH("/user/profile")
    suspend fun updateProfile(
        @Part profile: RequestBody,
        @Part image: MultipartBody.Part?
    ): BaseResponse<UserResponse>
}