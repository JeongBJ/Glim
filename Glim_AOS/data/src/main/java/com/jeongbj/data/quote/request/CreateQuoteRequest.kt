package com.jeongbj.data.quote.request

import com.google.gson.annotations.SerializedName

data class CreateQuoteRequest(
    @SerializedName("isbn13") val isbn13: String,
    @SerializedName("content") val content: String
)
