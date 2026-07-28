package com.jeongbj.data.block.usecase

import com.jeongbj.core.common.Cursor
import com.jeongbj.core.common.CursorPage
import com.jeongbj.domain.block.repository.BlockRepository
import com.jeongbj.domain.block.usecase.GetBlockedQuotesUseCase
import com.jeongbj.domain.quote.model.QuoteThumbnail
import javax.inject.Inject

class GetBlockedQuotesUseCaseImpl @Inject constructor(
    private val blockRepository: BlockRepository
): GetBlockedQuotesUseCase {
    override suspend fun invoke(cursor: Cursor): CursorPage<QuoteThumbnail, Long> =
        blockRepository.getBlockedQuotes(cursor)
}