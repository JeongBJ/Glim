package com.jeongbj.data.book.usecase

import com.jeongbj.core.common.ResultType
import com.jeongbj.core.common.flowResult
import com.jeongbj.domain.book.model.BookItemList
import com.jeongbj.domain.book.repository.BookRepository
import com.jeongbj.domain.book.usecase.GetHomeDataUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHomeDataUseCaseImpl @Inject constructor(
    private val bookRepository: BookRepository
) : GetHomeDataUseCase {
    override fun invoke(): Flow<ResultType<BookItemList>> = flowResult {
        bookRepository.getHomeData()
    }
}