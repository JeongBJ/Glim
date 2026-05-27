package com.jeongbj.presentation.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jeongbj.domain.book.model.Book
import com.jeongbj.domain.quote.model.Quote
import com.jeongbj.domain.user.model.User
import com.jeongbj.presentation.common.preview.Previews
import com.jeongbj.presentation.feature.home.component.BookCarousel
import com.jeongbj.presentation.feature.home.component.HomeItemSection
import com.jeongbj.presentation.feature.home.component.HomeLogoSection
import com.jeongbj.presentation.feature.home.component.QuoteCarousel
import com.jeongbj.presentation.theme.GlimTheme

@Composable
fun HomeContent(state: HomeState, onAction: (HomeAction) -> Unit) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        if (maxWidth < 600.dp) {
            HomePortrait(state, onAction)
        } else {
            HomeLandscape()
        }
    }
}

@Composable
fun HomePortrait(state: HomeState, onAction: (HomeAction) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) {
        item {
            HomeLogoSection()
            Spacer(modifier = Modifier.height(20.dp))
        }

        item {
            HomeItemSection("오늘의 글귀") {
                QuoteCarousel(
                    quotes = state.quotes,
                    onQuoteClick = { onAction(HomeAction.OnQuoteClick(it)) },
                )
            }
        }

        item {
            HomeItemSection("베스트 셀러") {
                BookCarousel(
                    books = state.books,
                    onBookClick = { onAction(HomeAction.OnBookClick(it)) }
                )
            }
        }

        item {
            HomeItemSection("추천 신간") {
                BookCarousel(
                    books = state.books,
                    onBookClick = { onAction(HomeAction.OnBookClick(it)) }
                )
            }
        }
    }
}

@Composable
fun HomeLandscape() {

}

@Previews
@Composable
fun HomeContentPreview() {
    GlimTheme {
        HomeContent(
            state = HomeState(
                quotes = listOf(
                    Quote(
                        0,
                        "",
                        "content",
                        10,
                        false,
                        User("nickname"),
                        Book("title", "", author = "author", isbn13 = "")
                    ),
                    Quote(
                        1,
                        "",
                        "content",
                        10,
                        false,
                        User("nickname"),
                        Book("title", "", author = "author", isbn13 = "")
                    ),
                    Quote(
                        2,
                        "",
                        "content",
                        10,
                        false,
                        User("nickname"),
                        Book("title", "", author = "author", isbn13 = "")
                    ),
                ),
                books = listOf(
                    Book("title", "", author = "author", isbn13 = ""),
                    Book("title", "", author = "author", isbn13 = ""),
                    Book("title", "", author = "author", isbn13 = "")
                )

            ),
            onAction = { }
        )
    }
}