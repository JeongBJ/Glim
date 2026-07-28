package com.jeongbj.data.fcm.entity

import kotlinx.serialization.Serializable

@Serializable
data class FcmTokenEntity(
    val token: String,
    val enabled: Boolean
)