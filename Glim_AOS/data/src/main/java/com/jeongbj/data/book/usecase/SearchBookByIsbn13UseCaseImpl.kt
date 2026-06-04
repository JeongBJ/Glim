package com.jeongbj.data.book.usecase

import com.jeongbj.core.common.ResultType
import com.jeongbj.core.common.flowResult
import com.jeongbj.domain.book.model.BookDetail
import com.jeongbj.domain.book.repository.BookRepository
import com.jeongbj.domain.book.usecase.SearchBookByIsbn13UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchBookByIsbn13UseCaseImpl @Inject constructor(
    private val bookRepository: BookRepository
) : SearchBookByIsbn13UseCase {
    override fun invoke(isbn13: String): Flow<ResultType<BookDetail>> = flowResult{
        bookRepository.searchBookByIsbn13(isbn13)
    }
}