package com.jeongbj.data.book.response

data class BookDetailResponse(
    val title: String,
    val coverUrl: String,
    val linkUrl: String? = null,
    val author: String,
    val translator: String? = null,
    val isbn13: String,
    val description: String? = null,
    val pubDate: String? = null,
    val priceSales: Int? = null,
    val publisher: String? = null,
    val category: String? = null,
    val quotes: List<QuoteSummaryResponse>? = listOf()
)
