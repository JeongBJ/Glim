package com.jeongbj.data.quote.usecase

import com.jeongbj.core.common.ResultType
import com.jeongbj.core.common.flowResult
import com.jeongbj.domain.quote.repository.QuoteRepository
import com.jeongbj.domain.quote.usecase.GenerateImageUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GenerateImageUseCaseImpl @Inject constructor(
    private val quoteRepository: QuoteRepository
) : GenerateImageUseCase {
    override fun invoke(content: String): Flow<ResultType<ByteArray>> = flowResult {
        quoteRepository.generateImage(content)
    }
}