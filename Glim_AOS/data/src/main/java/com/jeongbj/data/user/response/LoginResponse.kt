package com.jeongbj.data.user.response

import com.jeongbj.data.auth.response.AuthTokenResponse

data class LoginResponse(
    val token: AuthTokenResponse,
    val user: UserResponse
)
