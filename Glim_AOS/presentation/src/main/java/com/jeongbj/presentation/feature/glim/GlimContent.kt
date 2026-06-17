package com.jeongbj.presentation.feature.glim

import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.paging.compose.LazyPagingItems
import com.jeongbj.domain.quote.model.Quote
import com.jeongbj.presentation.feature.glim.component.GlimItem

@Composable
fun GlimContent(
    state: GlimState,
    onAction: (GlimAction) -> Unit,
    quotes: LazyPagingItems<Quote>
) {
    val pagerState = rememberPagerState(
        pageCount = { quotes.itemCount }
    )

    VerticalPager(
        state = pagerState
    ) { page ->
        val quote = quotes[page] ?: return@VerticalPager
        GlimItem(
            quote = quote,
            onLikeClicked = { onAction(GlimAction.OnLikeClicked(quote.quoteSeq))},
            onShareClicked = { onAction(GlimAction.OnShareClicked(quote)) },
            onBookInfoClicked = { onAction(GlimAction.OnBookInfoClicked(it)) }
        )
    }
}