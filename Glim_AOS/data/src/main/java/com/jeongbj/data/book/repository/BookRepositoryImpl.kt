package com.jeongbj.data.book.repository

import com.jeongbj.core.common.PagingResult
import com.jeongbj.core.common.unwrap
import com.jeongbj.data.book.datasource.BookRemoteDataSource
import com.jeongbj.data.book.datasource.RecentQueryLocalDataSource
import com.jeongbj.data.book.mapper.toBook
import com.jeongbj.data.book.mapper.toBookDetail
import com.jeongbj.data.book.mapper.toBookItemList
import com.jeongbj.data.book.mapper.toDomain
import com.jeongbj.data.book.request.BookSearchRequest
import com.jeongbj.domain.book.model.Book
import com.jeongbj.domain.book.model.BookDetail
import com.jeongbj.domain.book.model.BookItemList
import com.jeongbj.domain.book.model.BookRank
import com.jeongbj.domain.book.model.BookSearchQueryType
import com.jeongbj.domain.book.model.QueryType
import com.jeongbj.domain.book.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val bookRemoteDataSource: BookRemoteDataSource,
    private val recentQueryLocalDataSource: RecentQueryLocalDataSource
): BookRepository {
    override suspend fun searchBook(
        query: String,
        queryType: BookSearchQueryType,
        page: Int,
        size: Int
    ): PagingResult<Book> {
        val result = bookRemoteDataSource.searchBook(BookSearchRequest(
            query, queryType, page, size
        ))

        return PagingResult(
            page = result.data.page,
            totalPage = result.data.totalPage,
            data = result.data.data.map { it.toBook() },
            hasNext = result.data.hasNext,
            totalElements = result.data.totalElements
        )
    }

    override suspend fun searchBookByIsbn13(isbn13: String): BookDetail =
        bookRemoteDataSource.searchBookByIsbn13(isbn13).unwrap().toBookDetail()

    override suspend fun getHomeData(): BookItemList =
        bookRemoteDataSource.getHomeData().unwrap().toBookItemList()

    override suspend fun getQueryRanking(): List<BookRank> =
        bookRemoteDataSource.getQueryRanking().unwrap().map { it.toDomain() }

    override suspend fun getRecentQuery(): Flow<List<BookRank>> =
        recentQueryLocalDataSource.get()

    override suspend fun saveRecentQuery(query: String, queryType: QueryType) =
        recentQueryLocalDataSource.save(query, queryType)

    override suspend fun clearRecentQuery() =
        recentQueryLocalDataSource.clear()
}