package com.jeongbj.presentation.feature.info

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.jeongbj.domain.quote.model.QuoteThumbnail
import com.jeongbj.presentation.common.preview.Previews
import com.jeongbj.presentation.feature.info.component.GlimGrassSection
import com.jeongbj.presentation.feature.info.component.InfoHeaderSection
import com.jeongbj.presentation.feature.info.component.ThumbnailItem
import com.jeongbj.presentation.theme.GlimTheme
import kotlinx.coroutines.flow.flowOf

@Composable
fun InfoContent(
    state: InfoState,
    onAction: (InfoAction) -> Unit,
    modifier: Modifier = Modifier,
    quotes: LazyPagingItems<QuoteThumbnail>
) {
    LazyVerticalGrid (
        modifier = modifier.fillMaxSize(),
        columns = GridCells.Fixed(3)
    ) {


        item(
            span = { GridItemSpan(maxLineSpan) }
        ) {
            InfoHeaderSection(
                onProfileImageClicked = { onAction(InfoAction.OnProfileImageClicked) },
                modifier = modifier,
                user = state.userInfo?.user,
                isOwner = state.isOwner
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        item (
            span = { GridItemSpan(maxLineSpan) }
        ) {
            GlimGrassSection(
                modifier = modifier.padding(horizontal = 8.dp),
                contributions = state.contributions
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        item (
            span = { GridItemSpan(maxLineSpan) }
        ) {
            SecondaryTabRow(
                selectedTabIndex = state.selectedTab.ordinal
            ) {
                GlimType.entries.forEach { tab ->
                    val text = when (tab) {
                        GlimType.OWN -> state.userInfo?.numQuotes ?: 0
                        GlimType.LIKED -> state.userInfo?.numLikes ?: 0
                    }
                    Tab(
                        selected = state.selectedTab == tab,
                        onClick = { onAction(InfoAction.OnTabSelected(tab)) },
                        icon = { Icon(painter = painterResource(tab.resId), contentDescription = null) },
                        text = { Text(text = text.toString(), style = MaterialTheme.typography.bodySmall) }

                    )
                }
            }
        }

        if(quotes.itemCount == 0) {
            val text = when (state.selectedTab) {
                GlimType.OWN -> "나의 감성을 공유해보세요"
                GlimType.LIKED -> "좋아요 한 글림이 없어요"
            }

            item (
                span = { GridItemSpan(maxLineSpan) }
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = modifier.padding(vertical = 40.dp),
                    textAlign = TextAlign.Center
                )
            }

            return@LazyVerticalGrid
        }

        items(quotes.itemCount) { index ->
            ThumbnailItem(
                quote = quotes[index]?: return@items,
                onClicked = { onAction(InfoAction.OnQuoteThumbnailClicked(it)) },
                modifier = Modifier.padding(2.dp)
            )
        }
    }
}

@Previews
@Composable
fun InfoContentPreview() {
    GlimTheme {
        InfoContent(
            state = InfoState(),
            onAction = {   },
            quotes = remember {
                flowOf(
                    PagingData.from(
                        listOf<QuoteThumbnail>(
                            QuoteThumbnail(0, null),
                            QuoteThumbnail(0, null),
                            QuoteThumbnail(0, null)
                        )
                    )
                )
            }.collectAsLazyPagingItems()
        )
    }
}