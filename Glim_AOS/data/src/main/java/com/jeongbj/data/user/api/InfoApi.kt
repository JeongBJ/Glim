package com.jeongbj.data.user.api

import com.jeongbj.core.common.BaseResponse
import com.jeongbj.core.common.CursorPage
import com.jeongbj.data.user.response.QuoteThumbnailResponse
import com.jeongbj.data.user.response.UserInfoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface InfoApi {
    @GET("info/{userSeq}")
    suspend fun getUserInfo(@Path("userSeq") userSeq: Long): BaseResponse<UserInfoResponse>

    @GET("info/liked/{userSeq}")
    suspend fun getLikedQuotes(
        @Path("userSeq") userSeq: Long,
        @Query("cursor") cursor: Long?,
        @Query("size") size: Int = 20
    ): BaseResponse<CursorPage<QuoteThumbnailResponse, Long>>

    @GET("info/quotes/{userSeq}")
    suspend fun getMyQuotes(
        @Path("userSeq") userSeq: Long,
        @Query("cursor") cursor: Long?,
        @Query("size") size: Int = 20
    ): BaseResponse<CursorPage<QuoteThumbnailResponse, Long>>

    @GET("info/liked/{quoteSeq}")
    suspend fun getUserLikedQuotes(
        @Path("userSeq") userSeq: Long,
        @Query("cursor") cursor: Long?,
        @Query("size") size: Int = 20,
    ): BaseResponse<CursorPage<QuoteThumbnailResponse, Long>>

    @GET("info/quotes/{quoteSeq}")
    suspend fun getUserQuotes(
        @Path("userSeq") userSeq: Long,
        @Query("cursor") cursor: Long?,
        @Query("size") size: Int = 20,
    ): BaseResponse<CursorPage<QuoteThumbnailResponse, Long>>
}