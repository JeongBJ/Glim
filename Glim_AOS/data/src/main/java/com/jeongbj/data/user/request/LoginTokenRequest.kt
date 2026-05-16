package com.jeongbj.data.user.request

import com.google.gson.annotations.SerializedName

data class LoginTokenRequest(
    @SerializedName("idToken") val idToken: String,
    @SerializedName("fcmToken") val fcmToken: String = ""
)
