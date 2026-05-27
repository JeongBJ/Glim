package com.jeongbj.data.quote.usecase

import com.jeongbj.core.common.MultipartImage
import com.jeongbj.core.common.ResultType
import com.jeongbj.core.common.flowResult
import com.jeongbj.domain.quote.model.CreateQuote
import com.jeongbj.domain.quote.model.Quote
import com.jeongbj.domain.quote.repository.QuoteRepository
import com.jeongbj.domain.quote.usecase.SaveQuoteUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveQuoteUseCaseImpl @Inject constructor(
    private val quoteRepository: QuoteRepository
) : SaveQuoteUseCase {
    override fun invoke(
        createQuote: CreateQuote,
        image: MultipartImage,
    ): Flow<ResultType<Quote>> = flowResult {
        quoteRepository.saveQuote(createQuote, image)
    }
}