package com.jeongbj.data.book.usecase

import com.jeongbj.domain.book.model.QueryType
import com.jeongbj.domain.book.repository.BookRepository
import com.jeongbj.domain.book.usecase.SaveRecentQueryUseCase
import javax.inject.Inject

class SaveRecentQueryUseCaseImpl @Inject constructor(
    private val bookRepository: BookRepository
): SaveRecentQueryUseCase {
    override suspend fun invoke(query: String, queryType: QueryType) =
        bookRepository.saveRecentQuery(query, queryType)
}