package com.jeongbj.data.fcm.request

import kotlinx.serialization.Serializable

@Serializable
data class FcmTokenRequest(
    val token: String,
    val enabled: Boolean
)