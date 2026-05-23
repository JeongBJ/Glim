package com.jeongbj.glim.book.mapper

import com.jeongbj.glim.book.dto.BookResponse
import com.jeongbj.glim.book.entity.Book
import com.jeongbj.glim.external.aladin.dto.response.AladinItemResponse
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
        coverUrl = coverUrl,
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

fun AladinItemResponse.toBookResponse(): BookResponse {
    return BookResponse(
        title = title,
        linkUrl = linkUrl,
        coverUrl = coverUrl,
        author = author,
        translator = translator,
        isbn13 = isbn13,
        description = description,
        pubDate = LocalDate.parse(pubDate),
        priceSales = priceSales,
        publisher = publisher
    )
}