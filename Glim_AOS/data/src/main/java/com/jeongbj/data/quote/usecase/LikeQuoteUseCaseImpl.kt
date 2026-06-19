package com.jeongbj.data.quote.usecase

import com.jeongbj.core.common.ResultType
import com.jeongbj.core.common.flowResult
import com.jeongbj.domain.quote.repository.QuoteRepository
import com.jeongbj.domain.quote.usecase.LikeQuoteUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LikeQuoteUseCaseImpl @Inject constructor(
    private val quoteRepository: QuoteRepository
): LikeQuoteUseCase {
    override fun invoke(quoteSeq: Long): Flow<ResultType<Unit>> = flowResult {
        quoteRepository.likeQuote(quoteSeq)
    }
}