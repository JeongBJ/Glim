package com.jeongbj.domain.block.usecase

import com.jeongbj.core.common.Cursor
import com.jeongbj.core.common.CursorPage
import com.jeongbj.core.common.ResultType
import com.jeongbj.domain.quote.model.QuoteThumbnail
import com.jeongbj.domain.user.model.User
import kotlinx.coroutines.flow.Flow

interface BlockQuoteUseCase {
    operator fun invoke(quoteSeq: Long): Flow<ResultType<Unit>>
}

interface BlockUserUseCase {
    operator fun invoke(userSeq: Long): Flow<ResultType<Unit>>
}

interface UnblockQuoteUseCase {
    operator fun invoke(quoteSeq: Long): Flow<ResultType<Unit>>
}

interface UnblockUserUseCase {
    operator fun invoke(userSeq: Long): Flow<ResultType<Unit>>
}

interface GetBlockedQuotesUseCase {
    suspend operator fun invoke(cursor: Cursor): CursorPage<QuoteThumbnail, Long>
}

interface GetBlockedUsersUseCase {
    suspend operator fun invoke(cursor: Cursor): CursorPage<User, Long>
}

