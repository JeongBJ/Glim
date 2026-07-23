package com.jeongbj.data.user.response

import com.google.gson.annotations.SerializedName

data class UserInfoResponse(
    @SerializedName("user") val user: UserResponse,
    @SerializedName("numLikes") val numLikes: Int,
    @SerializedName("numQuotes") val numQuotes: Int,
    @SerializedName("contributions") val contributions: List<GlimContributionResponse>
)
