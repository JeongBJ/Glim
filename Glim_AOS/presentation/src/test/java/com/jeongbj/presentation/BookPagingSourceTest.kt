package com.jeongbj.presentation

import android.util.Log
import androidx.paging.PagingSource
import com.jeongbj.core.common.PagingResult
import com.jeongbj.domain.book.model.Book
import com.jeongbj.domain.book.model.BookSearchQueryType
import com.jeongbj.domain.book.usecase.SearchBookUseCase
import com.jeongbj.presentation.feature.book.search.paging.BookPagingSource
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BookPagingSourceTest {

    private val useCase = mockk<SearchBookUseCase>()

    private lateinit var pagingSource: BookPagingSource

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        every { Log.e(any(), any(), any()) } returns 0
        pagingSource = BookPagingSource(
            searchBookUseCase = useCase,
            query = "kotlin",
            type = BookSearchQueryType.TITLE
        )
    }

    @Test
    fun `load returns page successfully`() = runTest {
        val books = listOf(
            Book(
                isbn13 = "123",
                title = "title",
                author = "author",
                coverUrl = "url"
            )
        )

        coEvery {
            useCase(
                query = any(),
                type = any(),
                page = any(),
                size = any()
            )
        } returns PagingResult(
            page = 0,
            totalPage = 1,
            data = books,
            hasNext = false,
            totalElements = 1
        )

        // when
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 0,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        // then
        assert(result is PagingSource.LoadResult.Page)
        val page = result as PagingSource.LoadResult.Page
        assert(page.data.size == 1)
        assert(page.nextKey == null)
    }

    @Test
    fun `load returns error when exception occurs`() = runTest {

        // given
        coEvery {
            useCase(any(), any(), any(), any())
        } throws IllegalArgumentException()

        // when
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 0,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        // then
        assert(result is PagingSource.LoadResult.Error)
    }
}