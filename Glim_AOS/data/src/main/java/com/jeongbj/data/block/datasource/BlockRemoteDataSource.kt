package com.jeongbj.data.block.datasource

import com.jeongbj.core.common.BaseResponse
import com.jeongbj.core.common.CursorPage
import com.jeongbj.data.block.api.BlockApi
import com.jeongbj.data.user.response.UserResponse
import com.jeongbj.domain.quote.model.QuoteThumbnail
import javax.inject.Inject

class BlockRemoteDataSource @Inject constructor(
    private val blockApi: BlockApi
) {
    suspend fun blockQuote(quoteSeq: Long): BaseResponse<Unit> =
        blockApi.blockQuote(quoteSeq)

    suspend fun blockUser(userSeq: Long): BaseResponse<Unit> =
        blockApi.blockUser(userSeq)

    suspend fun unblockQuote(quoteSeq: Long): BaseResponse<Unit> =
        blockApi.unblockQuote(quoteSeq)

    suspend fun unblockUser(userSeq: Long): BaseResponse<Unit> =
        blockApi.unblockUser(userSeq)

    suspend fun getBlockedQuotes(cursor: Long?, size: Int): BaseResponse<CursorPage<QuoteThumbnail, Long>> =
        blockApi.getBlockedQuotes(cursor, size)

    suspend fun getBlockedUsers(cursor: Long?, size: Int): BaseResponse<CursorPage<UserResponse, Long>> =
        blockApi.getBlockedUsers(cursor, size)

}