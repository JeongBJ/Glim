package com.jeongbj.domain.book.model

import com.jeongbj.domain.quote.model.QuoteRank

data class BookItemList(
    val quotes: List<QuoteRank>,
    val bestSeller: List<Book>,
    val newSpecial: List<Book>,
    val editorChoice: List<Book>
)
