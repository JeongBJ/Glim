package com.jeongbj.data.book.response

data class BookItemListResponse(
    val todayQuotes: List<QuoteRankResponse>,
    val bestSeller: List<BookResponse>,
    val newSpecial: List<BookResponse>,
    val editorChoice: List<BookResponse>
)