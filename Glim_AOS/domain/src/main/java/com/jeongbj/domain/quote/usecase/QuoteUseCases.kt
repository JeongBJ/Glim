package com.jeongbj.domain.quote.usecase

import com.jeongbj.core.common.MultipartImage
import com.jeongbj.core.common.ResultType
import com.jeongbj.domain.quote.model.CreateQuote
import com.jeongbj.domain.quote.model.Quote
import kotlinx.coroutines.flow.Flow

interface GenerateImageUseCase {
    operator fun invoke(content: String): Flow<ResultType<ByteArray>>
}

interface SaveQuoteUseCase {
    operator fun invoke(createQuote: CreateQuote, image: MultipartImage): Flow<ResultType<Quote>>
}