package com.jeongbj.domain.user.repository

import com.jeongbj.core.common.Cursor
import com.jeongbj.core.common.CursorPage
import com.jeongbj.domain.quote.model.QuoteThumbnail
import com.jeongbj.domain.user.model.UserInfo

interface InfoRepository {
    suspend fun getUserInfo(): UserInfo
    suspend fun getLikedQuotes(cursor: Cursor): CursorPage<QuoteThumbnail, Long>
    suspend fun getMyQuotes(cursor: Cursor): CursorPage<QuoteThumbnail, Long>
    suspend fun getUserLikedQuotes(userSeq:Long, cursor: Cursor): CursorPage<QuoteThumbnail, Long>
    suspend fun getUserQuotes(userSeq: Long, cursor: Cursor): CursorPage<QuoteThumbnail, Long>
}