package com.jeongbj.data.quote.request

data class CreateQuoteRequest(
    val isbn13: String,
    val content: String
)
