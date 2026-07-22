package com.jeongbj.data.book.response

data class QuoteSummaryResponse(
    val quoteSeq: Long,
    val content: String,
    val numViews: Long,
    val numLikes: Long,
    val liked: Boolean,
)
