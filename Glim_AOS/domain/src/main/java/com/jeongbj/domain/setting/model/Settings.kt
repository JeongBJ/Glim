package com.jeongbj.domain.setting.model

data class Settings(
    val autoLoginEnabled: Boolean = false,
    val lockScreenEnabled: Boolean = false,
    val pushEnabled: Boolean = false
)
