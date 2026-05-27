package com.jeongbj.domain.book.model

data class BookItemList(
    val bestSeller: List<Book>,
    val newSpecial: List<Book>,
    val editorChoice: List<Book>
)
