package com.jeongbj.data.block.usecase

import com.jeongbj.core.common.ResultType
import com.jeongbj.core.common.flowResult
import com.jeongbj.domain.block.repository.BlockRepository
import com.jeongbj.domain.block.usecase.BlockUserUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BlockUserUseCaseImpl @Inject constructor(
    private val blockRepository: BlockRepository
): BlockUserUseCase {
    override fun invoke(userSeq: Long): Flow<ResultType<Unit>> = flowResult {
        blockRepository.blockUser(userSeq)
    }
}