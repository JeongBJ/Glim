package com.jeongbj.data.user.datasource

import com.jeongbj.core.common.BaseResponse
import com.jeongbj.data.fcm.request.FcmTokenRequest
import com.jeongbj.data.user.api.UserApi
import com.jeongbj.data.user.response.UserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val userApi: UserApi
) {

    suspend fun updateProfile(
        profile: RequestBody,
        image: MultipartBody.Part?
    ) : BaseResponse<UserResponse> =
        userApi.updateProfile(profile, image)

    suspend fun updateFcmToken(fcmTokenRequest: FcmTokenRequest) : BaseResponse<Unit> =
        userApi.updateFcmToken(fcmTokenRequest)
}