package com.jeongbj.data.fcm.request

import com.google.gson.annotations.SerializedName

data class FcmTokenRequest(
    @SerializedName("token")
    val token: String,

    @SerializedName("enabled")
    val enabled: Boolean
)