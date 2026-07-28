package com.jeongbj.domain.book.model

import com.jeongbj.domain.quote.model.QuoteSummary
import java.time.LocalDate

data class BookDetail(
    val title: String,
    val coverUrl: String,
    val linkUrl: String? = null,
    val author: String,
    val translator: String? = null,
    val isbn13: String,
    val description: String? = null,
    val pubDate: LocalDate? = null,
    val priceSales: Int? = null,
    val publisher: String? = null,
    val category: String? = null,
    val quotes: List<QuoteSummary> = listOf()
)
