package com.jeongbj.data.quote.usecase

import com.jeongbj.core.common.CursorPage
import com.jeongbj.domain.quote.model.Quote
import com.jeongbj.domain.quote.model.QuoteCursor
import com.jeongbj.domain.quote.repository.QuoteRepository
import com.jeongbj.domain.quote.usecase.GetQuotesUseCase
import javax.inject.Inject

class GetQuotesUseCaseImpl @Inject constructor(
    private val quoteRepository: QuoteRepository
): GetQuotesUseCase {
    override suspend fun invoke(
        quoteCursor: QuoteCursor?,
        seed: Long?,
        size: Int,
    ): CursorPage<Quote, QuoteCursor> =
        quoteRepository.getQuotes(
            seed = seed,
            cursor = quoteCursor,
            size = size
        )
}