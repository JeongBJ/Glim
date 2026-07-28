package com.jeongbj.glim.book.mapper

import com.jeongbj.glim.book.dto.BookDetailResponse
import com.jeongbj.glim.book.dto.BookResponse
import com.jeongbj.glim.book.entity.Book
import com.jeongbj.glim.external.aladin.dto.response.AladinItemResponse
import com.jeongbj.glim.quote.dto.QuoteBookResponse
import com.jeongbj.glim.quote.entity.Quote
import com.jeongbj.glim.quote.mapper.toSummaryResponse
import java.time.LocalDate

fun Book.toResponse(): BookResponse {
    return BookResponse(
        isbn13 = isbn13,
        title = title,
        coverUrl = coverUrl,
        linkUrl = linkUrl,
        author = author,
        description = description,
        pubDate = pubDate,
        translator = translator,
        priceSales = priceSales,
        publisher = publisher
    )
}

fun AladinItemResponse.toEntity(): Book {
    return Book(
        title = title,
        linkUrl = linkUrl,
        coverUrl = coverUrl.replace("coversum", "cover"),
        author = author,
        translator = translator,
        isbn13 = isbn13,
        description = description,
        pubDate = LocalDate.parse(pubDate),
        priceSales = priceSales,
        categoryId = categoryId,
        categoryName = categoryName,
        publisher = publisher
    )
}

fun Book.toQuoteResponse(): QuoteBookResponse =
    QuoteBookResponse(
        bookSeq = bookSeq,
        title = title,
        coverUrl = coverUrl,
        author = author,
        isbn13 = isbn13
    )

fun Book.toDetailResponse(likeQuoteSet: Set<Long>, quotes: List<Quote>): BookDetailResponse = BookDetailResponse(
    title = title,
    coverUrl = coverUrl,
    linkUrl = linkUrl,
    author = author,
    translator = translator,
    isbn13 = isbn13,
    description = description,
    pubDate = pubDate,
    priceSales = priceSales,
    publisher = publisher,
    quotes = quotes.map {
        it.toSummaryResponse(liked = it.quoteSeq in likeQuoteSet)
    }
)