package com.jeongbj.presentation.common.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jeongbj.domain.quote.model.Quote
import com.jeongbj.domain.quote.model.QuoteCursor
import com.jeongbj.domain.quote.usecase.GetQuotesUseCase

class QuotePagingSource(
    private val getQuotesUseCase: GetQuotesUseCase,
) : PagingSource<QuoteCursor, Quote>() {
    private var seed: Long? = null


    override suspend fun load(params: LoadParams<QuoteCursor>): LoadResult<QuoteCursor, Quote> {
        return try {
            val result = getQuotesUseCase(
                quoteCursor = params.key,
                size = params.loadSize,
                seed = seed
            )

            seed = seed ?: result.seed

            LoadResult.Page(
                data = result.items,
                prevKey = null,
                nextKey = result.nextCursor
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<QuoteCursor, Quote>)
    : QuoteCursor? =
        null
}