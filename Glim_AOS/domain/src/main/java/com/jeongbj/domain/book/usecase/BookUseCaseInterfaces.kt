package com.jeongbj.domain.book.usecase

import com.jeongbj.core.common.PagingResult
import com.jeongbj.core.common.ResultType
import com.jeongbj.domain.book.model.Book
import com.jeongbj.domain.book.model.BookItemList
import com.jeongbj.domain.book.model.BookSearchQueryType
import kotlinx.coroutines.flow.Flow

interface SearchBookUseCase {
    suspend operator fun invoke(query: String, type: BookSearchQueryType, page: Int, size: Int)
    : PagingResult<Book>
}

interface SearchBookByIsbn13UseCase {
    operator fun invoke(isbn13: String): Flow<ResultType<Book>>
}

interface GetHomeDataUseCase {
    operator fun invoke(): Flow<ResultType<BookItemList>>
}