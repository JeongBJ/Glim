package com.jeongbj.glim.quote.dto

data class QuoteResponse(
    val quoteSeq: Long,
    val imageUrl: String,
    val content: String,
    val numViews: Long,
    val numLikes: Long,
    val liked: Boolean,
    val user: QuoteUserResponse,
    val book: QuoteBookResponse
)
