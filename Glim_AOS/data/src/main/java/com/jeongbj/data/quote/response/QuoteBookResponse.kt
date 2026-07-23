package com.jeongbj.data.quote.response

import com.google.gson.annotations.SerializedName

data class QuoteBookResponse (
    @SerializedName("isbn13") val isbn13: String,
    @SerializedName("title") val title: String,
    @SerializedName("coverUrl") val coverUrl: String,
    @SerializedName("author") val author: String,
)