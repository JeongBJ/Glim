package com.jeongbj.data.user.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("userSeq") val userSeq: Long,
    @SerializedName("nickname") val nickname: String?,
    @SerializedName("imageUrl") val imageUrl: String?
)
