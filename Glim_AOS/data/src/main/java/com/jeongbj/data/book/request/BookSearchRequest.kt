package com.jeongbj.data.book.request

import com.google.gson.annotations.SerializedName
import com.jeongbj.domain.book.model.BookSearchQueryType

data class BookSearchRequest(
    @SerializedName("query") val query: String,
    @SerializedName("queryType") val queryType: BookSearchQueryType,
    @SerializedName("page") val page: Int,
    @SerializedName("size") val size: Int
)
