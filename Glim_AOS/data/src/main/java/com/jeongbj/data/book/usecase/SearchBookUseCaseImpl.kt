package com.jeongbj.data.book.usecase

import com.jeongbj.core.common.PagingResult
import com.jeongbj.domain.book.model.Book
import com.jeongbj.domain.book.model.BookSearchQueryType
import com.jeongbj.domain.book.repository.BookRepository
import com.jeongbj.domain.book.usecase.SearchBookUseCase
import javax.inject.Inject

class SearchBookUseCaseImpl @Inject constructor(
    private val bookRepository: BookRepository
) : SearchBookUseCase {
    override suspend operator fun invoke(
        query: String,
        type: BookSearchQueryType,
        page: Int,
        size: Int
    ): PagingResult<Book> {

        return bookRepository.searchBook(
            query = query,
            queryType = type,
            page = page,
            size = size
        )
    }
}