package com.jeongbj.data.user.response

import com.google.gson.annotations.SerializedName
import com.jeongbj.data.auth.response.AuthTokenResponse

data class LoginResponse(
    @SerializedName("token") val token: AuthTokenResponse,
    @SerializedName("user") val user: UserResponse
)
