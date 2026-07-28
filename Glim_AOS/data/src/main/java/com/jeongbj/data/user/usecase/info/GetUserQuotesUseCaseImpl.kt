package com.jeongbj.data.user.usecase.info

import com.jeongbj.core.common.Cursor
import com.jeongbj.core.common.CursorPage
import com.jeongbj.domain.quote.model.QuoteThumbnail
import com.jeongbj.domain.user.repository.InfoRepository
import com.jeongbj.domain.user.usecase.GetUserQuotesUseCase
import javax.inject.Inject

class GetUserQuotesUseCaseImpl @Inject constructor(
    private val infoRepository: InfoRepository
): GetUserQuotesUseCase {
    override suspend fun invoke(
        userSeq: Long,
        cursor: Cursor,
    ): CursorPage<QuoteThumbnail, Long> =
        infoRepository.getUserQuotes(userSeq, cursor)
}