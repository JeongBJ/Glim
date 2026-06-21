package com.jeongbj.data.book.mapper

import com.jeongbj.core.common.toLocalDate
import com.jeongbj.data.book.request.BookRankRequest
import com.jeongbj.data.book.response.BookDetailResponse
import com.jeongbj.data.book.response.BookItemListResponse
import com.jeongbj.data.book.response.BookRankResponse
import com.jeongbj.data.book.response.BookResponse
import com.jeongbj.domain.book.model.Book
import com.jeongbj.domain.book.model.BookDetail
import com.jeongbj.domain.book.model.BookItemList
import com.jeongbj.domain.book.model.BookRank
import com.jeongbj.domain.book.model.QueryType
import java.time.LocalDate

fun BookResponse.toBook(): Book = Book(
    title = title,
    coverUrl = coverUrl,
    author = author,
    isbn13 = isbn13,
    description = description,
    pubDate = LocalDate.parse(pubDate),
    publisher = publisher
)

fun BookDetailResponse.toBook(): Book = Book(
    title = title,
    coverUrl = coverUrl,
    author = author,
    isbn13 = isbn13,
    description = description,
    pubDate = pubDate.toLocalDate(),
    publisher = publisher
)

fun BookDetailResponse.toBookDetail(): BookDetail = BookDetail(
    title = title,
    coverUrl = coverUrl,
    author = author,
    isbn13 = isbn13,
    description = description,
    pubDate = pubDate.toLocalDate(),
    publisher = publisher,
    linkUrl = linkUrl,
    translator = translator,
    priceSales = priceSales,
    category = category,
    quotes = quotes ?: listOf()
)


fun BookItemListResponse.toBookItemList(): BookItemList = BookItemList(
    bestSeller = bestSeller.map { it.toBook() },
    editorChoice = editorChoice.map { it.toBook() },
    newSpecial = newSpecial.map { it.toBook() }
)

fun BookRank.toRequest() = BookRankRequest(
    rank = rank,
    title = title,
    queryType = queryType.displayName
)

fun BookRankResponse.toDomain() = BookRank(
    rank = rank,
    title = title,
    queryType = QueryType.valueOf(queryType)
)