package com.jeongbj.data.quote.mapper

import com.jeongbj.core.common.CursorPage
import com.jeongbj.data.quote.request.CreateQuoteRequest
import com.jeongbj.data.quote.response.QuoteBookResponse
import com.jeongbj.data.quote.response.QuoteCursorResponse
import com.jeongbj.data.quote.response.QuoteResponse
import com.jeongbj.data.quote.response.QuoteUserResponse
import com.jeongbj.data.user.response.QuoteThumbnailResponse
import com.jeongbj.domain.book.model.Book
import com.jeongbj.domain.quote.model.CreateQuote
import com.jeongbj.domain.quote.model.Quote
import com.jeongbj.domain.quote.model.QuoteCursor
import com.jeongbj.domain.quote.model.QuoteThumbnail
import com.jeongbj.domain.user.model.User

fun QuoteResponse.toDomain(): Quote = Quote(
    quoteSeq = quoteSeq,
    imageUrl = imageUrl,
    content = content,
    numLikes = numLikes,
    liked = liked,
    user = user.toDomain(),
    book = book.toDomain()
)

fun QuoteUserResponse.toDomain(): User = User(
    userSeq = userSeq,
    nickname = nickname,
    imageUrl = imageUrl
)

fun QuoteBookResponse.toDomain(): Book = Book(
    title = title,
    coverUrl = coverUrl,
    author = author,
    isbn13 = isbn13
)

fun CreateQuote.toRequest(): CreateQuoteRequest = CreateQuoteRequest(
    isbn13 = isbn13,
    content = content
)

fun QuoteThumbnailResponse.toDomain(): QuoteThumbnail = QuoteThumbnail(
    quoteSeq = quoteSeq,
    imageUrl = imageUrl
)

fun CursorPage<QuoteThumbnailResponse, Long>.toDomain()
: CursorPage<QuoteThumbnail, Long> =
    CursorPage(
        items = this.items.map { it.toDomain() },
        hasNext = this.hasNext,
        nextCursor = this.nextCursor
    )

fun QuoteCursorResponse.toDomain() = QuoteCursor(
    score = score,
    quoteSeq = quoteSeq
)