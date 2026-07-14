package com.jeongbj.data.block.usecase

import com.jeongbj.core.common.ResultType
import com.jeongbj.core.common.flowResult
import com.jeongbj.domain.block.repository.BlockRepository
import com.jeongbj.domain.block.usecase.UnblockUserUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UnblockUserUseCaseImpl @Inject constructor(
    private val blockRepository: BlockRepository
): UnblockUserUseCase {
    override fun invoke(userSeq: Long): Flow<ResultType<Unit>> = flowResult {
        blockRepository.unblockUser(userSeq)
    }
}