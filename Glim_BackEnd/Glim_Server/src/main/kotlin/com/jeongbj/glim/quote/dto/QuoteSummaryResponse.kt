package com.jeongbj.glim.quote.dto

data class QuoteSummaryResponse(
    val quoteSeq: Long,
    val content: String,
    val numViews: Long,
    val numLikes: Long,
    val liked: Boolean,
)
