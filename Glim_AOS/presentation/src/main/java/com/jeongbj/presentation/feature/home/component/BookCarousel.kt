package com.jeongbj.presentation.feature.home.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.jeongbj.domain.book.model.Book
import com.jeongbj.presentation.common.component.GlimAsyncImage
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@Composable
fun BookCarousel(
    modifier: Modifier = Modifier,
    books: List<Book> = listOf(),
    onBookClick: (String) -> Unit,
) {
    if (books.isEmpty()) return

    val cardWidth = 140.dp
    val containerWidth = with(LocalDensity.current) {
        LocalWindowInfo.current.containerSize.width.toDp()
    }
    val sidePadding = (containerWidth - cardWidth) / 2
    val initialPage = remember(books.size) {
        Int.MAX_VALUE / 2 - (Int.MAX_VALUE / 2 % books.size)
    }

    val pagerState = rememberPagerState(
        initialPage = initialPage,
        pageCount = { Int.MAX_VALUE }
    )
    val scope = rememberCoroutineScope()

    val currentBookIndex by remember {
        derivedStateOf {
            pagerState.currentPage % books.size
        }
    }
    val currentBook = books[currentBookIndex]
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = sidePadding),
            pageSize = PageSize.Fixed(cardWidth),
            key = { page ->
                page
            }
        ) { page ->
            val book = books[page % books.size]
            BookPagerCard(
                book = book,
                pagerState = pagerState,
                page = page,
                onBookClick = { isbn13 ->
                    if (pagerState.currentPage == page) {
                        onBookClick(isbn13)
                    } else {
                        scope.launch {
                            pagerState.animateScrollToPage(page)
                        }
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }

        AnimatedContent(
            targetState = currentBook,
            transitionSpec = {
                slideInVertically { it } + fadeIn() togetherWith
                        slideOutVertically { -it } + fadeOut()
            },
            label = "book_info_animation"
        ) { book ->
            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                BookInfo(
                    title = book.title,
                    author = book.author,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun BookPagerCard(
    book: Book,
    pagerState: PagerState,
    page: Int,
    onBookClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .graphicsLayer {
                val pageOffset = (
                        (pagerState.currentPage - page)
                                + pagerState.currentPageOffsetFraction
                        ).absoluteValue

                val scale = lerp(0.6f, 1f, 1f - pageOffset.coerceIn(0f, 1f))
                val alpha = lerp(0.6f, 1f, 1f - pageOffset.coerceIn(0f, 1f))

                scaleX = scale
                scaleY = scale
                this.alpha = alpha
            }
            .clickable { onBookClick(book.isbn13) },
        shape = RoundedCornerShape(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            GlimAsyncImage(book.coverUrl)
        }
    }
}


@Composable
private fun BookInfo(
    title: String,
    author: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
        maxLines = 3,
        overflow = TextOverflow.Ellipsis,
        color = Color.Black,
        modifier = modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )

    Text(
        text = author,
        style = MaterialTheme.typography.labelMedium,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis,
        color = Color.Black,
        modifier = modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}