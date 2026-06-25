package com.jeongbj.presentation.feature.home

import com.jeongbj.domain.book.model.Book
import com.jeongbj.domain.quote.model.QuoteRank

data class HomeState(
    val quotes: List<QuoteRank> = listOf(),
    val bestSeller: List<Book> = listOf(),
    val editorChoice: List<Book> = listOf(),
    val newSpecial: List<Book> = listOf(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false
)

sealed interface HomeAction {
    data class OnQuoteClick(val quoteSeq: Long): HomeAction
    data class OnBookClick(val isbn13: String): HomeAction
    data object OnRefreshing: HomeAction
}

sealed interface HomeSideEffect {
    data class NavigateToBookDetail(val isbn13: String): HomeSideEffect
    data class NavigateToQuoteDetail(val quoteSeq: Long): HomeSideEffect
}