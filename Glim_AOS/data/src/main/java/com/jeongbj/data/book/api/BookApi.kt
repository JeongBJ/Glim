package com.jeongbj.data.book.api

import com.jeongbj.core.common.BaseResponse
import com.jeongbj.core.common.PagingResult
import com.jeongbj.data.book.request.BookSearchRequest
import com.jeongbj.data.book.response.BookDetailResponse
import com.jeongbj.data.book.response.BookItemListResponse
import com.jeongbj.data.book.response.BookResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BookApi {

    @POST("book/search")
    suspend fun searchBook(@Body bookSearchRequest: BookSearchRequest): BaseResponse<PagingResult<BookResponse>>

    @POST("book/search/{isbn13}")
    suspend fun searchBookByIsbn13(@Path("isbn13") isbn13: String): BaseResponse<BookDetailResponse>

    @GET("book")
    suspend fun getHomeData(): BaseResponse<BookItemListResponse>
}