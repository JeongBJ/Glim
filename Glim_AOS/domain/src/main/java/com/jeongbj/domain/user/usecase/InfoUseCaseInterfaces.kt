package com.jeongbj.domain.user.usecase

import com.jeongbj.core.common.Cursor
import com.jeongbj.core.common.CursorPage
import com.jeongbj.core.common.ResultType
import com.jeongbj.domain.quote.model.QuoteThumbnail
import com.jeongbj.domain.user.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface GetUserInfoUseCase {
    operator fun invoke(userSeq: Long): Flow<ResultType<UserInfo>>
}

interface GetLikedQuotesUseCase {
    suspend operator fun invoke(userSeq: Long, cursor: Cursor): CursorPage<QuoteThumbnail, Long>
}

interface GetMyQuotesUseCase {
    suspend operator fun invoke(userSeq: Long, cursor: Cursor): CursorPage<QuoteThumbnail, Long>
}

interface GetUserLikedQuotesUseCase {
    suspend operator fun invoke(userSeq: Long, cursor: Cursor): CursorPage<QuoteThumbnail, Long>
}

interface GetUserQuotesUseCase {
    suspend operator fun invoke(userSeq: Long, cursor: Cursor): CursorPage<QuoteThumbnail, Long>
}