package com.jeongbj.presentation.feature.home

import com.jeongbj.domain.book.model.Book
import com.jeongbj.domain.quote.model.Quote

data class HomeState(
    val quotes: List<Quote> = listOf(),
    val books: List<Book> = listOf()
)

sealed interface HomeAction {
    data class OnQuoteClick(val quoteSeq: Long): HomeAction
    data class OnBookClick(val isbn13: String): HomeAction
}