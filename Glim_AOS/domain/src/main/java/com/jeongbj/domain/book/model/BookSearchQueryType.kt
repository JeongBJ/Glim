package com.jeongbj.domain.book.model


enum class BookSearchQueryType(val value: String) {
    KEYWORD("Keyword"),
    TITLE("Title"),
    AUTHOR("Author"),
    PUBLISHER("Publisher")
}