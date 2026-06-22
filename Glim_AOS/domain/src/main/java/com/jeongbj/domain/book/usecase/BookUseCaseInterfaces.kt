package com.jeongbj.domain.book.usecase

import com.jeongbj.core.common.PagingResult
import com.jeongbj.core.common.ResultType
import com.jeongbj.domain.book.model.Book
import com.jeongbj.domain.book.model.BookDetail
import com.jeongbj.domain.book.model.BookItemList
import com.jeongbj.domain.book.model.BookRank
import com.jeongbj.domain.book.model.BookSearchQueryType
import com.jeongbj.domain.book.model.QueryType
import kotlinx.coroutines.flow.Flow

interface SearchBookUseCase {
    suspend operator fun invoke(query: String, type: BookSearchQueryType, page: Int, size: Int)
    : PagingResult<Book>
}

interface SearchBookByIsbn13UseCase {
    operator fun invoke(isbn13: String): Flow<ResultType<BookDetail>>
}

interface GetHomeDataUseCase {
    operator fun invoke(): Flow<ResultType<BookItemList>>
}

interface GetRecentQueryUseCase {
    suspend operator fun invoke(): Flow<List<BookRank>>
}

interface GetQueryRankUseCase {
    suspend operator fun invoke(queryType: QueryType): Flow<ResultType<List<BookRank>>>
}

interface SaveRecentQueryUseCase {
    suspend operator fun invoke(query: String, queryType: QueryType)
}

interface ClearRecentQueryUseCase {
    suspend operator fun invoke()
}