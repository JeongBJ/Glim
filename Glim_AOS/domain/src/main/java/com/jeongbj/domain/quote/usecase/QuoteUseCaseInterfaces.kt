package com.jeongbj.domain.quote.usecase

import com.jeongbj.core.common.CursorPage
import com.jeongbj.core.common.MultipartImage
import com.jeongbj.core.common.ResultType
import com.jeongbj.domain.quote.model.CreateQuote
import com.jeongbj.domain.quote.model.Quote
import com.jeongbj.domain.quote.model.QuoteCursor
import com.jeongbj.domain.quote.model.QuoteThumbnail
import kotlinx.coroutines.flow.Flow

interface GenerateImageUseCase {
    operator fun invoke(content: String): Flow<ResultType<ByteArray>>
}

interface SaveQuoteUseCase {
    operator fun invoke(createQuote: CreateQuote, image: MultipartImage): Flow<ResultType<Quote>>
}

interface GetQuotesUseCase {
    suspend operator fun invoke(quoteCursor: QuoteCursor?, seed: Long?, size: Int): CursorPage<Quote, QuoteCursor>
}

interface GetQuoteUseCase {
    operator fun invoke(quoteSeq: Long): Flow<ResultType<Quote>>
}

interface IncreaseViewUseCase {
    operator fun invoke(quoteSeq: Long): Flow<ResultType<Unit>>
}

interface LikeQuoteUseCase {
    operator fun invoke(quoteSeq: Long): Flow<ResultType<Unit>>
}

interface DeleteQuoteUseCase {
    operator fun invoke(quoteSeq: Long): Flow<ResultType<Unit>>
}

interface GetLockScreenQuotesUseCase {
    suspend operator fun invoke(quoteCursor: QuoteCursor?, seed: Long?, size: Int): CursorPage<QuoteThumbnail, QuoteCursor>
}