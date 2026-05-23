package com.jeongbj.glim.book.dto

data class BookItemListResponse(
    val bestSeller: List<BookResponse>,
    val newSpecial: List<BookResponse>,
    val editorChoice: List<BookResponse>
)
