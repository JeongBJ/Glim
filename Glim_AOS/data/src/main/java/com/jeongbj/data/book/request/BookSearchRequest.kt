package com.jeongbj.data.book.request

import com.jeongbj.domain.book.model.BookSearchQueryType

data class BookSearchRequest(
    val query: String,
    val queryType: BookSearchQueryType,
    val page: Int,
    val size: Int
)
