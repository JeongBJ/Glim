package com.jeongbj.glim.book.dto

import com.jeongbj.glim.external.aladin.type.ItemSearchQueryType

data class BookSearchRequest(
    val query: String,
    val queryType: ItemSearchQueryType,
    val page: Int,
    val size: Int
)
