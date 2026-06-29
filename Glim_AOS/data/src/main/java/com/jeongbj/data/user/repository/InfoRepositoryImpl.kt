package com.jeongbj.data.user.repository

import com.jeongbj.core.common.Cursor
import com.jeongbj.core.common.CursorPage
import com.jeongbj.core.common.unwrap
import com.jeongbj.data.user.datasource.InfoRemoteDataSource
import com.jeongbj.data.user.mapper.toDomain
import com.jeongbj.domain.quote.model.QuoteThumbnail
import com.jeongbj.domain.user.model.UserInfo
import com.jeongbj.domain.user.repository.InfoRepository
import javax.inject.Inject

class InfoRepositoryImpl @Inject constructor(
    private val infoRemoteDataSource: InfoRemoteDataSource
): InfoRepository {
    override suspend fun getUserInfo(): UserInfo {
        return infoRemoteDataSource.getUserInfo().unwrap().toDomain()
    }

    override suspend fun getLikedQuotes(cursor: Cursor): CursorPage<QuoteThumbnail, Long> =
        infoRemoteDataSource.getLikedQuotes(cursor.cursor, cursor.size).unwrap()

    override suspend fun getMyQuotes(cursor: Cursor): CursorPage<QuoteThumbnail, Long> =
        infoRemoteDataSource.getMyQuotes(cursor.cursor, cursor.size).unwrap()

    override suspend fun getUserLikedQuotes(userSeq: Long, cursor: Cursor): CursorPage<QuoteThumbnail, Long> =
        infoRemoteDataSource.getUserLikedQuotes(userSeq, cursor.cursor, cursor.size).unwrap()

    override suspend fun getUserQuotes(userSeq: Long, cursor: Cursor): CursorPage<QuoteThumbnail, Long> =
        infoRemoteDataSource.getUserQuotes(userSeq, cursor.cursor, cursor.size).unwrap()
}