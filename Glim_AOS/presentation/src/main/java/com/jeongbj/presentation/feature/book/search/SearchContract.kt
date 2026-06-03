package com.jeongbj.presentation.feature.book.search

import com.jeongbj.domain.book.model.Book
import com.jeongbj.domain.book.model.BookRank
import com.jeongbj.domain.book.model.BookSearchQueryType
import com.jeongbj.domain.quote.model.Quote

data class SearchState(
    val query: String = "",
    val searchMode: SearchMode = SearchMode.POPULAR,
    val recentQuery: List<BookRank> = listOf(),
    val popularQuery: List<BookRank> = listOf(),
    val selectedTab: SearchTab = SearchTab.BOOK,
    val searchResultBook: List<Book> = listOf(),
    val searchResultQuote: List<Quote> = listOf(),
    val searchFilter: SearchFilter = SearchFilter.ALL,
    val totalBookElements: Long = 0,
    val totalQuoteElements: Long = 0,
)

sealed interface SearchAction {
    data class OnQuoteClick(val quoteSeq: Long): SearchAction
    data class OnBookClick(val isbn13: String): SearchAction
    data object OnBuyBookClick: SearchAction
    data class OnRegisterQuoteClick(val book: Book): SearchAction
    data object OnBackClick: SearchAction
    data class OnTextChanged(val query: String): SearchAction
    data object OnSearchClick: SearchAction
    data class OnQueryClick(val query: String, val mode: SearchMode): SearchAction
    data class OnSelectedTabChanged(val selectedTab: SearchTab): SearchAction
    data class OnFilterSelected(val filter: SearchFilter): SearchAction

}


sealed interface SearchSideEffect {
    data object ScrollToTop: SearchSideEffect
}

enum class SearchMode {
    POPULAR,
    RECENT,
    RESULT
}

enum class SearchTab(val displayName: String) {
    BOOK("도서"),
    QUOTE("글림"),
}

enum class SearchFilter(val displayName: String, val type: BookSearchQueryType) {
    ALL(displayName = "전체", type = BookSearchQueryType.KEYWORD),
    TITLE(displayName = "제목", type = BookSearchQueryType.TITLE),
    AUTHOR(displayName = "작가", type = BookSearchQueryType.AUTHOR),
    PUBLISHER(displayName = "출판사", type = BookSearchQueryType.PUBLISHER),
}