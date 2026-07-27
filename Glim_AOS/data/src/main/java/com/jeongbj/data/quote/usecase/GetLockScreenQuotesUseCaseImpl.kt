package com.jeongbj.data.quote.usecase

import com.jeongbj.core.common.CursorPage
import com.jeongbj.domain.quote.model.QuoteCursor
import com.jeongbj.domain.quote.model.QuoteThumbnail
import com.jeongbj.domain.quote.repository.QuoteRepository
import com.jeongbj.domain.quote.usecase.GetLockScreenQuotesUseCase
import javax.inject.Inject

class GetLockScreenQuotesUseCaseImpl @Inject constructor(
    private val quoteRepository: QuoteRepository,
) : GetLockScreenQuotesUseCase {
    override suspend fun invoke(
        quoteCursor: QuoteCursor?,
        seed: Long?,
        size: Int,
    ): CursorPage<QuoteThumbnail, QuoteCursor> =
        quoteRepository.getLockScreenQuotes(
            seed = seed,
            cursor = quoteCursor,
            size = size
        )

}