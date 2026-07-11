package com.jeongbj.domain.quote.repository

import com.jeongbj.core.common.CursorPage
import com.jeongbj.core.common.MultipartImage
import com.jeongbj.domain.quote.model.CreateQuote
import com.jeongbj.domain.quote.model.Quote
import com.jeongbj.domain.quote.model.QuoteCursor

interface QuoteRepository {
    suspend fun saveQuote(createQuote: CreateQuote, image: MultipartImage): Quote

    suspend fun generateImage(content: String): ByteArray

    suspend fun getQuotes(seed: Long?, cursor: QuoteCursor?, size: Int): CursorPage<Quote, QuoteCursor>

    suspend fun getQuote(quoteSeq: Long): Quote

    suspend fun increaseView(quoteSeq: Long)

    suspend fun likeQuote(quoteSeq: Long)

    suspend fun blockQuote(quoteSeq: Long)

    suspend fun deleteQuote(quoteSeq: Long)
}