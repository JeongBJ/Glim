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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.jeongbj.domain.quote.model.Quote
import com.jeongbj.presentation.common.component.GlimAsyncImage

@Composable
fun QuoteCarousel(
    quotes: List<Quote>,
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

                Spacer(modifier = Modifier.height(8.dp))

            }
        }
    }
}