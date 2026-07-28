package com.jeongbj.glim.book.dto

import com.jeongbj.glim.quote.dto.QuoteRankResponse

data class BookItemListResponse(
    val todayQuotes: List<QuoteRankResponse>,
    val bestSeller: List<BookResponse>,
    val newSpecial: List<BookResponse>,
    val editorChoice: List<BookResponse>
)
