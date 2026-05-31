package com.jeongbj.presentation.feature.book.search.component

import android.R.attr.mode
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.jeongbj.domain.book.model.BookRank
import com.jeongbj.presentation.common.preview.Previews
import com.jeongbj.presentation.feature.book.search.SearchAction
import com.jeongbj.presentation.feature.book.search.SearchMode
import com.jeongbj.presentation.feature.book.search.SearchState
import com.jeongbj.presentation.theme.GlimTheme

@Composable
fun QueryListSection(
    title: String,
    state: SearchState,
    queries: List<BookRank>,
    onAction: (SearchAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start
    ){
        Text(text = title)
        Spacer(modifier = Modifier.height(16.dp))
        queries.forEach {
            QueryListItem(it, state, onAction)
        }
    }
}

@Composable
private fun QueryListItem(item: BookRank, state: SearchState, onAction: (SearchAction) -> Unit) {
    Row(
        modifier = Modifier
            .clickable { onAction(SearchAction.OnQueryClick(item.title, state.searchMode)) }
            .padding(vertical = 8.dp),

        verticalAlignment = Alignment.CenterVertically
    ) {
        val rank = when (item.rank) {
            1 -> "🥇"
            2 -> "🥈"
            3 -> "🥉"
            else -> " ${item.rank}"
        }
        Text(
            text = rank,
            modifier = Modifier.width(32.dp),
            color = Color.Gray,
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = item.title,
            style = MaterialTheme.typography.bodyLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Previews
@Composable
private fun QueryListItemPreview() {
    GlimTheme {
        QueryListSection(
            "검색어 순위",
            SearchState(),
            listOf(
                BookRank(1, "title 영역"),
                BookRank(2, "title 영역"),
                BookRank(3, "title 영역 "),
                BookRank(4, "title 영역"),
            ), { })
    }
}