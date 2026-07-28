package com.jeongbj.data.book.usecase

import com.jeongbj.domain.book.repository.BookRepository
import com.jeongbj.domain.book.usecase.ClearRecentQueryUseCase
import javax.inject.Inject

class ClearRecentQueryUseCaseImpl @Inject constructor(
    private val bookRepository: BookRepository
): ClearRecentQueryUseCase {
    override suspend fun invoke() =
        bookRepository.clearRecentQuery()
}