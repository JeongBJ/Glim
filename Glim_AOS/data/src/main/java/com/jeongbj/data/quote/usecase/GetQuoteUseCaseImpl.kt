package com.jeongbj.data.quote.usecase

import com.jeongbj.core.common.ResultType
import com.jeongbj.core.common.flowResult
import com.jeongbj.domain.quote.model.Quote
import com.jeongbj.domain.quote.repository.QuoteRepository
import com.jeongbj.domain.quote.usecase.GetQuoteUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetQuoteUseCaseImpl @Inject constructor(
    private val quoteRepository: QuoteRepository
): GetQuoteUseCase {
    override fun invoke(quoteSeq: Long): Flow<ResultType<Quote>> = flowResult {
        quoteRepository.getQuote(quoteSeq)
    }
}