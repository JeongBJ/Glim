package com.jeongbj.data.block.repository

import com.jeongbj.core.common.Cursor
import com.jeongbj.core.common.CursorPage
import com.jeongbj.core.common.map
import com.jeongbj.core.common.unwrap
import com.jeongbj.data.block.datasource.BlockRemoteDataSource
import com.jeongbj.data.user.mapper.toDomain
import com.jeongbj.domain.block.repository.BlockRepository
import com.jeongbj.domain.quote.model.QuoteThumbnail
import com.jeongbj.domain.user.model.User
import javax.inject.Inject

class BlockRepositoryImpl @Inject constructor(
    private val blockRemoteDataSource: BlockRemoteDataSource
): BlockRepository {
    override suspend fun blockQuote(quoteSeq: Long) =
        blockRemoteDataSource.blockQuote(quoteSeq).unwrap()

    override suspend fun blockUser(userSeq: Long) =
        blockRemoteDataSource.blockUser(userSeq).unwrap()


    override suspend fun unblockQuote(quoteSeq: Long) =
        blockRemoteDataSource.unblockQuote(quoteSeq).unwrap()

    override suspend fun unblockUser(userSeq: Long) =
        blockRemoteDataSource.unblockUser(userSeq).unwrap()


    override suspend fun getBlockedQuotes(cursor: Cursor): CursorPage<QuoteThumbnail, Long> =
        blockRemoteDataSource.getBlockedQuotes(cursor.cursor, cursor.size).unwrap()


    override suspend fun getBlockedUsers(cursor: Cursor): CursorPage<User, Long> =
        blockRemoteDataSource.getBlockedUsers(cursor.cursor, cursor.size).unwrap().map {
            it.toDomain()
        }

}