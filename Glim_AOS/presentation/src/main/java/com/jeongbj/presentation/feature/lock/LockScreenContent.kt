package com.jeongbj.presentation.feature.lock

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.jeongbj.domain.quote.model.QuoteThumbnail
import com.jeongbj.presentation.common.preview.Previews
import com.jeongbj.presentation.feature.lock.component.LockScreenButtons
import com.jeongbj.presentation.feature.lock.component.LockScreenClock
import com.jeongbj.presentation.feature.lock.component.LockScreenItem
import kotlinx.coroutines.flow.flowOf

@Composable
fun LockScreenContent(
    modifier: Modifier = Modifier,
    onAction: (LockScreenAction) -> Unit,
    quotes: LazyPagingItems<QuoteThumbnail>
) {
    val pagerState = rememberPagerState(
        pageCount = { quotes.itemCount }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        VerticalPager(
            state = pagerState
        ) { page ->
            val quote = quotes[page] ?: return@VerticalPager
            Box(
                modifier = Modifier.fillMaxSize()
                    .align(Alignment.Center)
            ) {
                LockScreenItem(
                    quote = quote,
                    onSaveClicked = { onAction(LockScreenAction.OnSaveClicked(it)) },
                )
            }
        }
        LockScreenClock(
            modifier = Modifier.align(
                BiasAlignment(
                    horizontalBias = 0f,
                    verticalBias = -0.8f
                )
            )
        )
        LockScreenButtons(
            onOpenGlimClicked = { onAction(LockScreenAction.OnOpenGlimClicked(quotes[pagerState.currentPage]?.quoteSeq ?: 0)) },
            onOpenCameraClicked = { onAction(LockScreenAction.OnOpenCameraClicked) },
            onUnlocked = { onAction(LockScreenAction.OnUnlocked) }
        )
    }
}


@Previews
@Composable
fun LockScreenContentPreview() {
    LockScreenContent(
        onAction = {},
        quotes = remember {
            flowOf(
                PagingData.from(
                    listOf(
                        QuoteThumbnail(0, "")
                    )
                )
            )
        }.collectAsLazyPagingItems()
    )
}