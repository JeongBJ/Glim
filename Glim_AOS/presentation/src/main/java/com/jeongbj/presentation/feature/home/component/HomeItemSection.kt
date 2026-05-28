package com.jeongbj.presentation.feature.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jeongbj.domain.book.model.Book
import com.jeongbj.domain.quote.model.Quote
import com.jeongbj.domain.user.model.User
import com.jeongbj.presentation.common.preview.Previews
import com.jeongbj.presentation.theme.GlimTheme

@Composable
fun HomeItemSection(
    title: String,
    content: @Composable () -> Unit,
) {
    Column(modifier = Modifier) {
        SectionTitle(title)

        content()

        HorizontalDivider(
            modifier = Modifier.padding(top = 16.dp),
            thickness = 8.dp,
            color = Color(0xFFF7F7F7)
        )
    }
}

@Previews
@Composable
fun HomeItemSectionPreview() {
    GlimTheme {
        HomeItemSection("title", {
            QuoteCarousel(
                listOf(
                    Quote(
                        0,
                        "",
                        "",
                        12,
                        false,
                        User("nickname"),
                        Book("title", "", author = "author", isbn13 = "3")
                    ),
                    Quote(
                        1,
                        "",
                        "",
                        12,
                        false,
                        User("nickname"),
                        Book("title", "", author = "author", isbn13 = "2")
                    ),
                    Quote(
                        2,
                        "",
                        "",
                        12,
                        false,
                        User("nickname"),
                        Book("title", "", author = "author", isbn13 = "1")
                    )
                ), { })
        })
    }
}