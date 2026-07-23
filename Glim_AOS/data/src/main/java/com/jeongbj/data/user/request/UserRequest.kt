package com.jeongbj.data.user.request

import com.google.gson.annotations.SerializedName

data class UserRequest(
    @SerializedName("nickname") val nickname: String,
    @SerializedName("imageUrl") val imageUrl: String? = null
)

