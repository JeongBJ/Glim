package com.jeongbj.presentation.feature.book.detail

import com.jeongbj.domain.book.model.BookDetail
import java.time.LocalDate


data class BookDetailState(
    val book: BookDetail = BookDetail(
        title = "title",
        "",
        author = "author",
        isbn13 = "isbn",
        category = "category",
        publisher = "publisher",
        description = "description",
        pubDate = LocalDate.now(),
        priceSales = 12345,
    ),
    val isExpanded: Boolean = false,
    val isLoading: Boolean = false
)

sealed interface BookDetailAction {
    data object OnBackClick: BookDetailAction
    data class OnBuyBookClick(val linkUrl: String): BookDetailAction
    data class OnRegisterQuoteClick(val bookDetail: BookDetail): BookDetailAction
    data object ToggleBookDescriptionExpanded: BookDetailAction
    data class OnClickQuote(val quoteSeq: Long): BookDetailAction
}

sealed interface BookDetailSideEffect {
    data class OpenUrl(val url: String): BookDetailSideEffect
    data object NavigateBack: BookDetailSideEffect
}