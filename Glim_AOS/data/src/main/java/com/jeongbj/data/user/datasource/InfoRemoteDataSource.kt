package com.jeongbj.data.user.datasource

import com.jeongbj.core.common.BaseResponse
import com.jeongbj.core.common.CursorPage
import com.jeongbj.data.user.api.InfoApi
import com.jeongbj.data.user.response.UserInfoResponse
import com.jeongbj.domain.quote.model.QuoteThumbnail
import javax.inject.Inject

class InfoRemoteDataSource @Inject constructor(
    private val infoApi: InfoApi
) {
    suspend fun getUserInfo(userSeq: Long): BaseResponse<UserInfoResponse> =
        infoApi.getUserInfo(userSeq)

    suspend fun getLikedQuotes(userSeq: Long, cursor: Long?, size: Int)
    : BaseResponse<CursorPage<QuoteThumbnail, Long>> =
        infoApi.getLikedQuotes(userSeq, cursor, size)

    suspend fun getMyQuotes(userSeq: Long, cursor: Long?, size: Int)
    : BaseResponse<CursorPage<QuoteThumbnail, Long>> =
        infoApi.getMyQuotes(userSeq, cursor, size)

    suspend fun getUserLikedQuotes(userSeq: Long, cursor: Long?, size: Int)
    : BaseResponse<CursorPage<QuoteThumbnail, Long>> =
        infoApi.getUserLikedQuotes(userSeq, cursor, size)

    suspend fun getUserQuotes(userSeq: Long, cursor: Long?, size: Int)
    : BaseResponse<CursorPage<QuoteThumbnail, Long>> =
        infoApi.getUserQuotes(userSeq, cursor, size)
}