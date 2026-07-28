package com.jeongbj.glim.quote.dto

data class QuotePageRequest(
    val seed: Long? = null,
    val cursor: QuoteCursor? = null,
    val size: Int = 20,
)
