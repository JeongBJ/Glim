package com.jeongbj.data.book.response

import com.google.gson.annotations.SerializedName

data class QuoteSummaryResponse(
    @SerializedName("quoteSeq") val quoteSeq: Long,
    @SerializedName("content") val content: String,
    @SerializedName("numViews") val numViews: Long,
    @SerializedName("numLikes") val numLikes: Long,
    @SerializedName("liked") val liked: Boolean,
)
