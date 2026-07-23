package com.jeongbj.data.quote.response

import com.google.gson.annotations.SerializedName

data class QuoteUserResponse(
    @SerializedName("userSeq") val userSeq: Long,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("imageUrl") val imageUrl: String
)

