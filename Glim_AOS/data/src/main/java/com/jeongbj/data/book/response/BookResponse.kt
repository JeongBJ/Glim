package com.jeongbj.data.book.response

data class BookResponse(
    val title: String,
    val coverUrl: String,
    val author: String,
    val isbn13: String,
    val description: String?,
    val pubDate: String
)
