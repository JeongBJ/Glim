package com.jeongbj.presentation.feature.book.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.jeongbj.domain.book.model.Book
import com.jeongbj.domain.book.model.BookRank
import com.jeongbj.presentation.common.component.LoadingOverlay
import com.jeongbj.presentation.common.preview.Previews
import com.jeongbj.presentation.feature.book.search.component.QueryListSection
import com.jeongbj.presentation.feature.book.search.component.SearchResultSection
import com.jeongbj.presentation.feature.book.search.component.SearchTopSection
import com.jeongbj.presentation.feature.book.search.component.bookResultSection
import com.jeongbj.presentation.theme.GlimTheme
import kotlinx.coroutines.flow.flowOf

@Composable
fun SearchContent(
    state: SearchState,
    onAction: (SearchAction) -> Unit,
    books: LazyPagingItems<Book>
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        if (maxWidth < 600.dp) {
            SearchPortrait(state, onAction, books)
        } else {
            SearchLandscape(state, onAction, books)
        }
    }

}

@Composable
fun SearchPortrait(
    state: SearchState,
    onAction: (SearchAction) -> Unit,
    books: LazyPagingItems<Book>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) {
        item {
            SearchTopSection(state, onAction)
            Spacer(modifier = Modifier.height(16.dp))
        }

        if(state.searchMode != SearchMode.RESULT) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                ) {
                    if (state.recentQuery.isNotEmpty()) {
                        QueryListSection(
                            title = "최근 검색어",
                            queries = state.recentQuery,
                            state = state,
                            onAction = onAction,
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                    }
                    QueryListSection(
                        title = "검색어 랭킹",
                        queries = state.popularQuery,
                        state = state,
                        onAction = onAction,
                    )
                }
            }
        } else {
            item {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    SearchResultSection(
                        state = state,
                        isPortrait = true,
                        onAction = onAction,
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    if(books.loadState.refresh == LoadState.Loading) {
                        LoadingOverlay(backgroundColor = Color.White)
                    }
                    when (state.selectedTab) {
                        SearchTab.BOOK -> {
                            this@LazyColumn.bookResultSection(
                                books = books,
                                onAction = onAction
                            )
                        }

                        SearchTab.QUOTE -> {

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SearchLandscape(
    state: SearchState,
    onAction: (SearchAction) -> Unit,
    books: LazyPagingItems<Book>
) {

}

@Previews
@Composable
fun SearchContentPreview() {
    GlimTheme {
        SearchContent(
            state = SearchState(
                searchMode = SearchMode.RESULT,
                recentQuery = listOf(
                    BookRank(1, "title 영역title 영역title 영역title 영역 title 영역"),
                    BookRank(2, "title"),
                    BookRank(3, "title"),
                    BookRank(4, "title"),
                    BookRank(5, "title"),
                    BookRank(6, "title"),
                    BookRank(7, "title"),
                    BookRank(8, "title"),
                    BookRank(9, "title"),
                    BookRank(10, "title"),
                    ),
                popularQuery = listOf(
                    BookRank(1, "title 영역title 영역title 영역title 영역"),
                    BookRank(2, "title"),
                    BookRank(3, "title"),
                    BookRank(4, "title"),
                    BookRank(5, "title"),
                    BookRank(6, "title"),
                    BookRank(7, "title"),
                    BookRank(8, "title"),
                    BookRank(9, "title"),
                    BookRank(10, "title"),
                    )
            ),
            books = remember {
                flowOf(
                    PagingData.from(
                        listOf(
                            Book(
                                title = "클린 코드",
                                isbn13 = "123",
                                author = "로버트 마틴",
                                coverUrl = "",
                                description = "descriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescription"
                            )
                        )
                    )
                )
            }.collectAsLazyPagingItems(),
            onAction = { }
        )
    }
}