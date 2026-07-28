package com.jeongbj.domain.block.repository

import com.jeongbj.core.common.Cursor
import com.jeongbj.core.common.CursorPage
import com.jeongbj.domain.quote.model.QuoteThumbnail
import com.jeongbj.domain.user.model.User

interface BlockRepository {

    suspend fun blockQuote(quoteSeq: Long)

    suspend fun blockUser(userSeq: Long)

    suspend fun unblockQuote(quoteSeq: Long)

    suspend fun unblockUser(userSeq: Long)

    suspend fun getBlockedQuotes(cursor: Cursor): CursorPage<QuoteThumbnail, Long>

    suspend fun getBlockedUsers(cursor: Cursor): CursorPage<User, Long>

}