package com.jeongbj.data.user.usecase.info

import com.jeongbj.core.common.Cursor
import com.jeongbj.core.common.CursorPage
import com.jeongbj.domain.quote.model.QuoteThumbnail
import com.jeongbj.domain.user.repository.InfoRepository
import com.jeongbj.domain.user.usecase.GetLikedQuotesUseCase
import javax.inject.Inject

class GetLikedQuotesUseCaseImpl @Inject constructor(
    private val infoRepository: InfoRepository
): GetLikedQuotesUseCase {
    override suspend fun invoke(userSeq: Long, cursor: Cursor): CursorPage<QuoteThumbnail, Long> =
        infoRepository.getLikedQuotes(userSeq, cursor)

}