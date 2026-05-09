package com.jeongbj.domain.login.model

data class LoginToken(
    val idToken: String,
    val fcmToken: String = ""
)
