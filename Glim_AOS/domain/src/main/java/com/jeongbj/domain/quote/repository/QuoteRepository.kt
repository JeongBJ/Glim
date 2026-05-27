package com.jeongbj.domain.quote.repository

import com.jeongbj.core.common.MultipartImage
import com.jeongbj.domain.quote.model.CreateQuote
import com.jeongbj.domain.quote.model.Quote

interface QuoteRepository {
    suspend fun saveQuote(createQuote: CreateQuote, image: MultipartImage): Quote

    suspend fun generateImage(content: String): ByteArray
}