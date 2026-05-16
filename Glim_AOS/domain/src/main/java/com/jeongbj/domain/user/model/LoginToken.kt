package com.jeongbj.domain.user.model

data class LoginToken(
    val idToken: String,
    val fcmToken: String = ""
)
