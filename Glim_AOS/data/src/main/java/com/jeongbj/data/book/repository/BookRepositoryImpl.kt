package com.jeongbj.data.book.repository

import com.jeongbj.core.common.PagingResult
import com.jeongbj.core.common.unwrap
import com.jeongbj.data.book.datasource.BookRemoteDataSource
import com.jeongbj.data.book.mapper.toBook
import com.jeongbj.data.book.mapper.toBookItemList
import com.jeongbj.data.book.request.BookSearchRequest
import com.jeongbj.domain.book.model.Book
import com.jeongbj.domain.book.model.BookItemList
import com.jeongbj.domain.book.model.BookSearchQueryType
import com.jeongbj.domain.book.repository.BookRepository
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val bookRemoteDataSource: BookRemoteDataSource,
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

    override suspend fun searchBookByIsbn13(isbn13: String): Book =
        bookRemoteDataSource.searchBookByIsbn13(isbn13).unwrap().toBook()

    override suspend fun getHomeData(): BookItemList =
        bookRemoteDataSource.getHomeData().unwrap().toBookItemList()
}