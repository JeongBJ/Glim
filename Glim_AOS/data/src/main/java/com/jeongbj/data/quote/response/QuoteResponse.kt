package com.jeongbj.data.quote.response

data class QuoteResponse(
    val quoteSeq: Long,
    val imageUrl: String,
    val content: String,
    val numLikes: Long,
    val liked: Boolean,
    val user: QuoteUserResponse,
    val book: QuoteBookResponse
)

