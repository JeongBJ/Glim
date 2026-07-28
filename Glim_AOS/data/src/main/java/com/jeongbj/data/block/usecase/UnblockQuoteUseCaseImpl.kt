package com.jeongbj.data.block.usecase

import com.jeongbj.core.common.ResultType
import com.jeongbj.core.common.flowResult
import com.jeongbj.domain.block.repository.BlockRepository
import com.jeongbj.domain.block.usecase.UnblockQuoteUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UnblockQuoteUseCaseImpl @Inject constructor(
    private val blockRepository: BlockRepository
): UnblockQuoteUseCase {
    override fun invoke(quoteSeq: Long): Flow<ResultType<Unit>> = flowResult {
        blockRepository.unblockQuote(quoteSeq)
    }
}