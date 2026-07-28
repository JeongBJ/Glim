package com.jeongbj.data.block.api

import com.jeongbj.core.common.BaseResponse
import com.jeongbj.core.common.CursorPage
import com.jeongbj.data.user.response.UserResponse
import com.jeongbj.domain.quote.model.QuoteThumbnail
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BlockApi {

    @GET("block/quote/{quoteSeq}")
    suspend fun blockQuote(@Path("quoteSeq") quoteSeq: Long): BaseResponse<Unit>

    @GET("block/user/{userSeq}")
    suspend fun blockUser(@Path("userSeq") userSeq: Long): BaseResponse<Unit>

    @DELETE("block/quote/{quoteSeq}")
    suspend fun unblockQuote(@Path("quoteSeq") quoteSeq: Long): BaseResponse<Unit>

    @DELETE("block/user/{userSeq}")
    suspend fun unblockUser(@Path("userSeq") userSeq: Long): BaseResponse<Unit>

    @GET("block/quote")
    suspend fun getBlockedQuotes(
        @Query("cursor") cursor: Long?,
        @Query("size") size: Int
    ): BaseResponse<CursorPage<QuoteThumbnail, Long>>

    @GET("block/user")
    suspend fun getBlockedUsers(
        @Query("cursor") cursor: Long?,
        @Query("size") size: Int
    ): BaseResponse<CursorPage<UserResponse, Long>>

}