package com.jeongbj.domain.auth.model

data class AuthToken(
    val accessToken: String,
    val refreshToken: String
)
