package com.jeongbj.data.quote.response

import com.google.gson.annotations.SerializedName

data class QuoteCursorResponse(
    @SerializedName("score") val score: Long,
    @SerializedName("quoteSeq") val quoteSeq: Long
)

