package com.jeongbj.glim.user.dto.request

data class LoginRequest(
    val idToken: String,
    val fcmToken: String?
)