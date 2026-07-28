package com.jeongbj.data.user.request

import com.google.gson.annotations.SerializedName
import com.jeongbj.data.fcm.request.FcmTokenRequest

data class LoginTokenRequest(
    @SerializedName("idToken") val idToken: String,
    @SerializedName("fcmTokenRequest") val fcmTokenRequest: FcmTokenRequest?
)
