package com.jeongbj.data.book.usecase

import com.jeongbj.domain.book.model.BookRank
import com.jeongbj.domain.book.repository.BookRepository
import com.jeongbj.domain.book.usecase.GetRecentQueryUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecentQueryUseCaseImpl @Inject constructor(
    private val bookRepository: BookRepository
): GetRecentQueryUseCase {
    override suspend fun invoke(): Flow<List<BookRank>> =
        bookRepository.getRecentQuery()

}