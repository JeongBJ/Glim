package com.jeongbj.glim.auth.dto.response

data class AuthTokenResponse(
    val accessToken: String,
    val refreshToken: String
)
