package com.jeongbj.glim.quote.mapper

import com.jeongbj.glim.info.dto.QuoteThumbnailResponse
import com.jeongbj.glim.quote.dto.*
import com.jeongbj.glim.quote.entity.Quote

fun Quote.toResponse(liked: Boolean, user: QuoteUserResponse, book: QuoteBookResponse): QuoteResponse = QuoteResponse(
    quoteSeq = quoteSeq,
    imageUrl = imageUrl,
    content = content,
    numViews = numViews,
    numLikes = numLikes,
    liked = liked,
    user = user,
    book = book
)

fun Quote.toSummaryResponse(
    liked: Boolean
): QuoteSummaryResponse {
    return QuoteSummaryResponse(
        quoteSeq = quoteSeq,
        content = content,
        numViews = numViews,
        numLikes = numLikes,
        liked = liked
    )
}

fun QuoteProjection.toQuoteResponse(): QuoteResponse = QuoteResponse(
    quoteSeq = quoteSeq,
    imageUrl = imageUrl,
    content = content,
    numViews = numViews,
    numLikes = numLikes,
    liked = liked,
    user = QuoteUserResponse(
        userSeq = userSeq,
        nickname = nickname,
        imageUrl = profileImageUrl
    ),
    book = QuoteBookResponse(
        bookSeq = bookSeq,
        title = title,
        coverUrl = coverUrl,
        author = author,
        isbn13 = isbn13
    )
)

fun QuoteThumbnailProjection.toQuoteThumbnailResponse() = QuoteThumbnailResponse(
    quoteSeq = quoteSeq,
    imageUrl = imageUrl
)

fun QuoteDetailProjection.toQuoteResponse(): QuoteResponse = QuoteResponse(
    quoteSeq = quoteSeq,
    imageUrl = imageUrl,
    content = content,
    numViews = numViews,
    numLikes = numLikes,
    liked = liked,
    user = QuoteUserResponse(
        userSeq = userSeq,
        nickname = nickname,
        imageUrl = profileImageUrl
    ),
    book = QuoteBookResponse(
        bookSeq = bookSeq,
        title = title,
        coverUrl = coverUrl,
        author = author,
        isbn13 = isbn13
    )
)

fun Quote.toQuoteRankResponse(): QuoteRankResponse = QuoteRankResponse(
    quoteSeq = quoteSeq,
    imageUrl = imageUrl,
    title = book.title,
    author = book.author,
)