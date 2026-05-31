package com.jeongbj.presentation.feature.book.search.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.jeongbj.domain.book.model.Book
import com.jeongbj.presentation.common.preview.Previews
import com.jeongbj.presentation.feature.book.search.SearchAction
import com.jeongbj.presentation.feature.book.search.SearchState
import com.jeongbj.presentation.feature.book.search.SearchTab
import com.jeongbj.presentation.theme.GlimTheme
import kotlinx.coroutines.flow.flowOf

@Composable
fun SearchResultSection(
    state: SearchState,
    isPortrait: Boolean,
    onAction: (SearchAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SecondaryTabRow(
            selectedTabIndex = state.selectedTab.ordinal,
        ) {
            SearchTab.entries.forEach { tab ->
                Tab(
                    selected = state.selectedTab == tab,
                    onClick = { onAction(SearchAction.OnSelectedTabChanged(tab)) },
                    text = {
                        Text(
                            text = tab.displayName,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                )
            }
        }

        if(state.selectedTab == SearchTab.BOOK) {
            SearchFilterChip(
                modifier = modifier.padding(vertical = 16.dp),
                state = state,
                onAction = onAction
            )
        } else {

        }

        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            val counts = if(state.selectedTab == SearchTab.BOOK)
                state.totalBookElements
            else
                state.totalQuoteElements
            Text(
                text = "'${state.query}' 검색 결과 ${counts}건",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}




@Previews
@Composable
fun SearchResultSectionPreview() {
    GlimTheme {
        SearchResultSection(
            state = SearchState(),
            isPortrait = true,
            onAction = { }
        )
    }
}
