package com.jeongbj.presentation.feature.book.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.jeongbj.domain.book.model.BookDetail
import com.jeongbj.domain.quote.model.QuoteSummary
import com.jeongbj.presentation.common.component.GlimAsyncImage
import com.jeongbj.presentation.common.component.GlimTopbar
import com.jeongbj.presentation.common.preview.Previews
import com.jeongbj.presentation.feature.book.detail.component.BookDetailButtons
import com.jeongbj.presentation.feature.book.detail.component.BookInfoSection
import com.jeongbj.presentation.feature.book.detail.component.ContentsSection
import com.jeongbj.presentation.feature.book.detail.component.QuoteCard
import com.jeongbj.presentation.theme.GlimTheme

@Composable
fun BookDetailContent(
    state: BookDetailState,
    onAction: (BookDetailAction) -> Unit,
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        if (maxWidth < 600.dp) {
            BookDetailPortrait(state, onAction)
        } else {
            BookDetailLandscape(state, onAction)
        }
    }
}

@Composable
fun BookDetailPortrait(
    state: BookDetailState,
    onAction: (BookDetailAction) -> Unit,
) {
    val quotes = state.book.quotes
    val pagerState = rememberPagerState(pageCount = { quotes.size })
    Column {
        GlimTopbar(
            title = state.book.title,
            onBackClick = { onAction(BookDetailAction.OnBackClick) }
        )

        Box {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                item {
                    GlimAsyncImage(state.book.coverUrl)
                    Spacer(Modifier.height(8.dp))
                }

                item {
                    BookInfoSection(state)
                    Spacer(Modifier.height(8.dp))
                    HorizontalDivider(thickness = 8.dp, color = Color(0xFFF7F7F7))
                }

                item {
                    Spacer(Modifier.height(24.dp))
                    ContentsSection("등록된 글림")
                    Spacer(Modifier.height(16.dp))
                    if (quotes.isEmpty()) {
                        Text(
                            text = "등록된 글림이 없습니다",
                            modifier = Modifier
                                .padding(top = 16.dp, bottom = 8.dp)
                                ,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxWidth(),
                        pageSpacing = 8.dp,
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) { page ->
                        QuoteCard(
                            quote = quotes[page],
                            modifier = Modifier.fillMaxWidth(),
                            onClickQuote = { onAction(BookDetailAction.OnClickQuote(it)) }
                        )
                    }
                }

                item {
                    Spacer(Modifier.height(24.dp))
                    HorizontalDivider(thickness = 8.dp, color = Color(0xFFF7F7F7))
                }

                item {
                    Spacer(Modifier.height(24.dp))
                    ContentsSection(
                        title = "도서 개요",
                        isExpanded = state.isExpanded,
                        onAction = { onAction(BookDetailAction.ToggleBookDescriptionExpanded) }
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = state.book.description ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = if (state.isExpanded) Int.MAX_VALUE else 5,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }

                item {
                    Spacer(Modifier.height(24.dp))
                    HorizontalDivider(thickness = 8.dp, color = Color(0xFFF7F7F7))
                }
            }

            BookDetailButtons(onAction = onAction, state = state)

        }


    }
}

@Composable
fun BookDetailLandscape(
    state: BookDetailState,
    onAction: (BookDetailAction) -> Unit,
) {
    val quotes = state.book.quotes
    val pagerState = rememberPagerState(pageCount = { quotes.size })
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        GlimTopbar(
            title = state.book.title,
            onBackClick = { onAction(BookDetailAction.OnBackClick) }
        )

        Row {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                GlimAsyncImage(
                    imageUrl = state.book.coverUrl,
                    modifier = Modifier.weight(1f)

                )
                Spacer(Modifier.height(16.dp))
                BookInfoSection(
                    state = state
                )
                Spacer(Modifier.height(16.dp))
            }

            VerticalDivider(thickness = 8.dp, color = Color(0xFFF7F7F7))

            Box (modifier = Modifier.weight(1f)) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    item {
                        Spacer(Modifier.height(24.dp))
                        ContentsSection("등록된 글림")
                        Spacer(Modifier.height(16.dp))
                        if (quotes.isEmpty()) {
                            Text(
                                text = "등록된 글림이 없습니다",
                                modifier = Modifier
                                    .padding(top = 16.dp, bottom = 8.dp),
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier.fillMaxWidth(),
                            pageSpacing = 8.dp,
                            contentPadding = PaddingValues(horizontal = 16.dp)
                        ) { page ->
                            QuoteCard(
                                quote = quotes[page],
                                modifier = Modifier.fillMaxWidth(),
                                onClickQuote = { onAction(BookDetailAction.OnClickQuote(it)) }
                            )
                        }
                    }

                    item {
                        Spacer(Modifier.height(24.dp))
                        HorizontalDivider(thickness = 8.dp, color = Color(0xFFF7F7F7))
                    }

                    item {
                        Spacer(Modifier.height(24.dp))
                        ContentsSection(
                            title = "도서 개요",
                            isExpanded = state.isExpanded,
                            onAction = { onAction(BookDetailAction.ToggleBookDescriptionExpanded) }
                        )
                        Spacer(Modifier.height(16.dp))
                        Text(
                            text = state.book.description ?: "",
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = if (state.isExpanded) Int.MAX_VALUE else 5,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }

                    item {
                        Spacer(Modifier.height(24.dp))
                        HorizontalDivider(thickness = 8.dp, color = Color(0xFFF7F7F7))
                    }
                }

                BookDetailButtons(onAction = onAction, state = state)
            }

        }
    }
}

@Previews
@Composable
fun BookDetailContentPreview() {
    GlimTheme {
        BookDetailContent(
            state = BookDetailState(
                book = BookDetail(
                    title = "title",
                    coverUrl = "",
                    author = "author",
                    isbn13 = "",
                    publisher = "publisher",
                    description = "description\ndescription\ndescription\ndescription\ndescription\ndescription",
                    quotes = listOf(
                        QuoteSummary(
                            0, "content", 10, 10, true
                        )
                    )
                ),
            )
        ) { }
    }
}