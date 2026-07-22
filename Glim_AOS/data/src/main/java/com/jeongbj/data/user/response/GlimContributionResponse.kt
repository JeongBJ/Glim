package com.jeongbj.data.user.response

import com.google.gson.annotations.SerializedName

data class GlimContributionResponse(
    @SerializedName("date") val date: String,
    @SerializedName("count") val count: Int
)
