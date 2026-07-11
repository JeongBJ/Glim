package com.jeongbj.data.quote.usecase

import com.jeongbj.core.common.ResultType
import com.jeongbj.core.common.flowResult
import com.jeongbj.domain.quote.repository.QuoteRepository
import com.jeongbj.domain.quote.usecase.BlockQuoteUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BlockQuoteUseCaseImpl @Inject constructor(
    private val quoteRepository: QuoteRepository
): BlockQuoteUseCase {
    override fun invoke(quoteSeq: Long): Flow<ResultType<Unit>> = flowResult {
        quoteRepository.blockQuote(quoteSeq)
    }
}