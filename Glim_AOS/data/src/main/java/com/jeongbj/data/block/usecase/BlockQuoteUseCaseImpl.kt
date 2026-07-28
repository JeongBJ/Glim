package com.jeongbj.data.block.usecase

import com.jeongbj.core.common.ResultType
import com.jeongbj.core.common.flowResult
import com.jeongbj.domain.block.repository.BlockRepository
import com.jeongbj.domain.block.usecase.BlockQuoteUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BlockQuoteUseCaseImpl @Inject constructor(
    private val blockRepository: BlockRepository
): BlockQuoteUseCase {
    override fun invoke(quoteSeq: Long): Flow<ResultType<Unit>> = flowResult {
        blockRepository.blockQuote(quoteSeq)
    }
}