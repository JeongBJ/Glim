package com.jeongbj.data.book.datasource

import com.jeongbj.core.common.BaseResponse
import com.jeongbj.core.common.PagingResult
import com.jeongbj.data.book.api.BookApi
import com.jeongbj.data.book.request.BookRankRequest
import com.jeongbj.data.book.request.BookSearchRequest
import com.jeongbj.data.book.response.BookDetailResponse
import com.jeongbj.data.book.response.BookItemListResponse
import com.jeongbj.data.book.response.BookRankResponse
import com.jeongbj.data.book.response.BookResponse
import javax.inject.Inject

class BookRemoteDataSource @Inject constructor(
    private val bookApi: BookApi
) {

    suspend fun searchBook(bookSearchRequest: BookSearchRequest): BaseResponse<PagingResult<BookResponse>> =
        bookApi.searchBook(bookSearchRequest)

    suspend fun searchBookByIsbn13(isbn13: String): BaseResponse<BookDetailResponse> =
        bookApi.searchBookByIsbn13(isbn13)

    suspend fun getHomeData(): BaseResponse<BookItemListResponse> =
        bookApi.getHomeData()

    suspend fun getQueryRanking(request: BookRankRequest): BaseResponse<List<BookRankResponse>> =
        bookApi.getQueryRanking(request)
}