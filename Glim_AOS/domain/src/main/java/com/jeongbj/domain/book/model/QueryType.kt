package com.jeongbj.domain.book.model

enum class QueryType(val displayName: String) {
    BOOK("도서"),
    QUOTE("글림");

    companion object {
        fun from(value: String): QueryType {
            return entries.firstOrNull {
                it.name == value ||
                        it.displayName == value
            } ?: BOOK
        }
    }
}