package com.jeongbj.glim.quote.dto

data class QuoteThumbnailProjection(
    val quoteSeq: Long,
    val imageUrl: String,
    val score: Long,
)