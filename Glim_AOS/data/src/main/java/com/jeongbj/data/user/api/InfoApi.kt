package com.jeongbj.data.user.api

import com.jeongbj.core.common.BaseResponse
import com.jeongbj.core.common.CursorPage
import com.jeongbj.data.user.response.UserInfoResponse
import com.jeongbj.domain.quote.model.QuoteThumbnail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface InfoApi {
    @GET("info")
    suspend fun getUserInfo(): BaseResponse<UserInfoResponse>

    @GET("info/liked")
    suspend fun getLikedQuotes(
        @Query("cursor") cursor: Long?,
        @Query("size") size: Int = 20
    ): BaseResponse<CursorPage<QuoteThumbnail, Long>>

    @GET("info/quotes")
    suspend fun getMyQuotes(
        @Query("cursor") cursor: Long?,
        @Query("size") size: Int = 20
    ): BaseResponse<CursorPage<QuoteThumbnail, Long>>

    @GET("info/liked/{quoteSeq}")
    suspend fun getUserLikedQuotes(
        @Path("userSeq") userSeq: Long,
        @Query("cursor") cursor: Long?,
        @Query("size") size: Int = 20,
    ): BaseResponse<CursorPage<QuoteThumbnail, Long>>

    @GET("info/quotes/{quoteSeq}")
    suspend fun getUserQuotes(
        @Path("userSeq") userSeq: Long,
        @Query("cursor") cursor: Long?,
        @Query("size") size: Int = 20,
    ): BaseResponse<CursorPage<QuoteThumbnail, Long>>
}