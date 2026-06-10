package com.jeongbj.glim.quote.dto

data class QuoteBookResponse (
    val bookSeq: Long,
    val title: String,
    val coverUrl: String,
    val author: String,
    val isbn13: String
)