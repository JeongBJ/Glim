package com.jeongbj.data.quote.request

import com.google.gson.annotations.SerializedName

data class GenerateImageRequest(
    @SerializedName("content") val content: String
)
