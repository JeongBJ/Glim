package com.jeongbj.presentation.common.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jeongbj.core.common.Cursor
import com.jeongbj.domain.block.usecase.BlockUseCases
import com.jeongbj.domain.user.model.User

class BlockedUserPagingSource(
    private val blockUseCases: BlockUseCases
): PagingSource<Long, User>() {
    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, User> {
        return try {
            val cursor = Cursor(params.key, params.loadSize)
            val result = blockUseCases.getBlockedUsersUseCase(cursor)

            LoadResult.Page(
                data = result.items,
                prevKey = null,
                nextKey = result.nextCursor
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Long, User>): Long? =
        null
}