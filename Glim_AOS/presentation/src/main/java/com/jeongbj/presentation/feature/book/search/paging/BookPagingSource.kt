package com.jeongbj.presentation.feature.book.search.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jeongbj.domain.book.model.Book
import com.jeongbj.domain.book.model.BookSearchQueryType
import com.jeongbj.domain.book.usecase.SearchBookUseCase
import javax.inject.Inject

class BookPagingSource @Inject constructor(
    private val searchBookUseCase: SearchBookUseCase,
    private val query: String,
    private val type: BookSearchQueryType,
    private val totalElements: (Long) -> Unit,
) : PagingSource<Int, Book>(){
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> {
        return try {
            val page = params.key ?: 0
            val result = searchBookUseCase(query, type, page, params.loadSize)

            if(page == 0) {
                totalElements(result.totalElements)
            }
            LoadResult.Page(
                data = result.data,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (result.hasNext) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Book>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }
}