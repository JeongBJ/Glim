package com.jeongbj.presentation.common.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jeongbj.domain.quote.model.QuoteCursor
import com.jeongbj.domain.quote.model.QuoteThumbnail
import com.jeongbj.domain.quote.usecase.GetLockScreenQuotesUseCase

class LockScreenPagingSource(
    private val getLockScreenQuotesUseCase: GetLockScreenQuotesUseCase,
) : PagingSource<QuoteCursor, QuoteThumbnail>() {
    private var seed: Long? = null
    override suspend fun load(params: LoadParams<QuoteCursor>): LoadResult<QuoteCursor, QuoteThumbnail> {
        return try {
            val result = getLockScreenQuotesUseCase(
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

    override fun getRefreshKey(state: PagingState<QuoteCursor, QuoteThumbnail>): QuoteCursor? =
        null
}