package com.jeongbj.presentation.feature.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.jeongbj.domain.quote.model.QuoteRank
import com.jeongbj.presentation.common.component.GlimAsyncImage
import com.jeongbj.presentation.common.preview.Previews
import com.jeongbj.presentation.theme.GlimTheme

@Composable
fun QuoteCarousel(
    quotes: List<QuoteRank>,
    onQuoteClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    itemSize: DpSize = DpSize(width = 240.dp, height = 360.dp),
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = quotes,
            key = { it.quoteSeq }
            ) { quote ->
            Column(
                modifier = Modifier
                    .width(itemSize.width)
                    .clickable { onQuoteClick(quote.quoteSeq) },
                horizontalAlignment = Alignment.Start
            ) {
                Card(
                    modifier = Modifier.size(itemSize),
                    shape = RoundedCornerShape(4.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    GlimAsyncImage(quote.imageUrl)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = quote.title,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = quote.author,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Previews
@Composable
fun QuoteCarouselPreview() {
    GlimTheme {
        QuoteCarousel(
            listOf(
                QuoteRank(
                    quoteSeq = 0,
                    imageUrl = "",
                    title = "title",
                    author = "author"
                )
            ),
            onQuoteClick = { },
        )
    }
}