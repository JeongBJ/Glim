package com.jeongbj.data.book.response

import com.google.gson.annotations.SerializedName

data class QuoteRankResponse(
    @SerializedName("quoteSeq") val quoteSeq: Long,
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("title") val title: String,
    @SerializedName("author") val author: String
)
