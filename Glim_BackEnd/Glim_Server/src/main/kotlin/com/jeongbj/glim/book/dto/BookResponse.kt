package com.jeongbj.glim.book.dto

import java.time.LocalDate

data class BookResponse(
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
