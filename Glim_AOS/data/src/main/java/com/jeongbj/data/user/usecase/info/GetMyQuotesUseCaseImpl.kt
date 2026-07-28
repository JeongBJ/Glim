package com.jeongbj.data.user.usecase.info

import com.jeongbj.core.common.Cursor
import com.jeongbj.core.common.CursorPage
import com.jeongbj.domain.quote.model.QuoteThumbnail
import com.jeongbj.domain.user.repository.InfoRepository
import com.jeongbj.domain.user.usecase.GetMyQuotesUseCase
import javax.inject.Inject

class GetMyQuotesUseCaseImpl @Inject constructor(
    private val infoRepository: InfoRepository
): GetMyQuotesUseCase {
    override suspend fun invoke(userSeq: Long, cursor: Cursor): CursorPage<QuoteThumbnail, Long> =
        infoRepository.getMyQuotes(userSeq, cursor)
}