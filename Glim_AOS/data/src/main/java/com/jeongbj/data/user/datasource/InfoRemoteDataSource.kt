package com.jeongbj.data.user.datasource

import com.jeongbj.core.common.BaseResponse
import com.jeongbj.core.common.CursorPage
import com.jeongbj.data.user.api.InfoApi
import com.jeongbj.data.user.response.QuoteThumbnailResponse
import com.jeongbj.data.user.response.UserInfoResponse
import javax.inject.Inject

class InfoRemoteDataSource @Inject constructor(
    private val infoApi: InfoApi
) {
    suspend fun getUserInfo(userSeq: Long): BaseResponse<UserInfoResponse> =
        infoApi.getUserInfo(userSeq)

    suspend fun getLikedQuotes(userSeq: Long, cursor: Long?, size: Int)
    : BaseResponse<CursorPage<QuoteThumbnailResponse, Long>> =
        infoApi.getLikedQuotes(userSeq, cursor, size)

    suspend fun getMyQuotes(userSeq: Long, cursor: Long?, size: Int)
    : BaseResponse<CursorPage<QuoteThumbnailResponse, Long>> =
        infoApi.getMyQuotes(userSeq, cursor, size)

    suspend fun getUserLikedQuotes(userSeq: Long, cursor: Long?, size: Int)
    : BaseResponse<CursorPage<QuoteThumbnailResponse, Long>> =
        infoApi.getUserLikedQuotes(userSeq, cursor, size)

    suspend fun getUserQuotes(userSeq: Long, cursor: Long?, size: Int)
    : BaseResponse<CursorPage<QuoteThumbnailResponse, Long>> =
        infoApi.getUserQuotes(userSeq, cursor, size)
}