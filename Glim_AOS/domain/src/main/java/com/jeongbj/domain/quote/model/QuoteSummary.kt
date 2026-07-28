package com.jeongbj.domain.quote.model

data class QuoteSummary(
    val quoteSeq: Long,
    val content: String,
    val numViews: Long,
    val numLikes: Long,
    val liked: Boolean,
)
