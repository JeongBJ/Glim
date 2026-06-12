package com.jeongbj.glim.quote.mapper

import com.jeongbj.glim.quote.dto.QuoteBookResponse
import com.jeongbj.glim.quote.dto.QuoteResponse
import com.jeongbj.glim.quote.dto.QuoteSummaryResponse
import com.jeongbj.glim.quote.dto.QuoteUserResponse
import com.jeongbj.glim.quote.entity.Quote

fun Quote.toResponse(liked: Boolean, user: QuoteUserResponse, book: QuoteBookResponse): QuoteResponse = QuoteResponse(
    quoteSeq = quoteSeq,
    imageUrl = imageUrl,
    content = content,
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