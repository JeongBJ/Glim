package com.jeongbj.glim.auth.dto.request

data class LoginRequest(
    val idToken: String,
    val fcmToken: String?
)
