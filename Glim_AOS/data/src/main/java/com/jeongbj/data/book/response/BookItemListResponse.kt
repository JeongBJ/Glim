package com.jeongbj.data.book.response

data class BookItemListResponse(
    val bestSeller: List<BookResponse>,
    val newSpecial: List<BookResponse>,
    val editorChoice: List<BookResponse>
)