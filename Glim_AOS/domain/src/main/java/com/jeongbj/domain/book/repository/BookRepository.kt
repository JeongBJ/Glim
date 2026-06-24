package com.jeongbj.domain.book.repository

import com.jeongbj.core.common.PagingResult
import com.jeongbj.domain.book.model.Book
import com.jeongbj.domain.book.model.BookDetail
import com.jeongbj.domain.book.model.BookItemList
import com.jeongbj.domain.book.model.BookRank
import com.jeongbj.domain.book.model.BookSearchQueryType
import com.jeongbj.domain.book.model.QueryType
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun searchBook(query: String, queryType: BookSearchQueryType, page: Int, size: Int): PagingResult<Book>

    suspend fun searchBookByIsbn13(isbn13: String): BookDetail

    suspend fun getHomeData(): BookItemList

    suspend fun getQueryRanking(): List<BookRank>

    suspend fun getRecentQuery(): Flow<List<BookRank>>

    suspend fun saveRecentQuery(query: String, queryType: QueryType)

    suspend fun clearRecentQuery()
}