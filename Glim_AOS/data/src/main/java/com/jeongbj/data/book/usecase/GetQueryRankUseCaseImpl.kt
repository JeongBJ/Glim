package com.jeongbj.data.book.usecase

import com.jeongbj.core.common.ResultType
import com.jeongbj.core.common.flowResult
import com.jeongbj.domain.book.model.BookRank
import com.jeongbj.domain.book.repository.BookRepository
import com.jeongbj.domain.book.usecase.GetQueryRankUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetQueryRankUseCaseImpl @Inject constructor(
    private val bookRepository: BookRepository
): GetQueryRankUseCase {
    override suspend fun invoke(): Flow<ResultType<List<BookRank>>> = flowResult {
        bookRepository.getQueryRanking()
    }
}



