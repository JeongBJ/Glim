package com.jeongbj.data.book.mapper

import com.jeongbj.data.book.response.BookDetailResponse
import com.jeongbj.data.book.response.BookItemListResponse
import com.jeongbj.data.book.response.BookResponse
import com.jeongbj.domain.book.model.Book
import com.jeongbj.domain.book.model.BookItemList

fun BookResponse.toBook(): Book = Book(
    title = title,
    coverUrl = coverUrl,
    author = author,
    isbn13 = isbn13,
    description = description,
    pubDate = pubDate
)

fun BookDetailResponse.toBook(): Book = Book(
    title = title,
    coverUrl = coverUrl,
    author = author,
    isbn13 = isbn13,
    description = description,
    pubDate = pubDate
)

fun BookItemListResponse.toBookItemList(): BookItemList = BookItemList(
    bestSeller = bestSeller.map { it.toBook() },
    editorChoice = editorChoice.map { it.toBook() },
    newSpecial = newSpecial.map { it.toBook() }
)