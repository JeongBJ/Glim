package com.jeongbj.data.book.response

import java.time.LocalDate

data class BookDetailResponse(
    val title: String,
    val coverUrl: String,
    val linkUrl: String,
    val author: String,
    val translator: String?,
    val isbn13: String,
    val description: String?,
    val pubDate: LocalDate,
    val priceSales: Int,
    val publisher: String
)
