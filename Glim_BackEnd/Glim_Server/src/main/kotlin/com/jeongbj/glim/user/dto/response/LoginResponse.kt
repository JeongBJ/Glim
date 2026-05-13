package com.jeongbj.glim.user.dto.response

import com.jeongbj.glim.auth.dto.response.AuthTokenResponse

data class LoginResponse(
    val token: AuthTokenResponse,
    val user: UserResponse
)
