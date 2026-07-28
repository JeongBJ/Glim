package com.jeongbj.presentation.feature.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jeongbj.domain.quote.model.QuoteRank
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
                quotes = listOf(
                    QuoteRank(
                        0,
                        "",
                        "content",
                        "author",
                    ),
                    QuoteRank(
                        1,
                        "",
                        "content",
                        "author",
                    ),
                    QuoteRank(
                        2,
                        "",
                        "content",
                        "author",
                    ),
                ),
                { })
        })
    }
}