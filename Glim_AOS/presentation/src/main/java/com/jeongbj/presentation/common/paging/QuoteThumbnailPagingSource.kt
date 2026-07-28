package com.jeongbj.presentation.common.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jeongbj.core.common.Cursor
import com.jeongbj.domain.block.usecase.BlockUseCases
import com.jeongbj.domain.quote.model.QuoteThumbnail
import com.jeongbj.domain.user.model.InfoQuotesType
import com.jeongbj.domain.user.usecase.InfoUseCases

class QuoteThumbnailPagingSource(
    private val infoUseCases: InfoUseCases? = null,
    private val blockUseCases: BlockUseCases? = null,
    private val type: InfoQuotesType,
    private val userSeq: Long = 0
): PagingSource<Long, QuoteThumbnail>() {

    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, QuoteThumbnail> {
        return try {
            val cursor = Cursor(params.key, params.loadSize)
            val result = when (type) {
                InfoQuotesType.MY -> infoUseCases!!.getMyQuotesUseCase(userSeq, cursor)
                InfoQuotesType.LIKED -> infoUseCases!!.getLikedQuotesUseCase(userSeq, cursor)
                InfoQuotesType.USER -> infoUseCases!!.getUserQuotesUseCase(userSeq, cursor)
                InfoQuotesType.USER_LIKED -> infoUseCases!!.getUserLikedQuotesUseCase(userSeq, cursor)
                InfoQuotesType.BLOCKED -> blockUseCases!!.getBlockedQuotesUseCase(cursor)
            }

            LoadResult.Page(
                data = result.items,
                prevKey = null,
                nextKey = result.nextCursor,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Long, QuoteThumbnail>): Long? =
        null
}