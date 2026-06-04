package com.jeongbj.presentation.feature.book.detail.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.jeongbj.domain.quote.model.QuoteSummary
import com.jeongbj.presentation.R
import com.jeongbj.presentation.common.preview.Previews
import com.jeongbj.presentation.theme.GlimTheme

@Composable
fun QuoteCard(
    quote: QuoteSummary,
    modifier: Modifier = Modifier,
    onClickQuote: (Long) -> Unit
) {
    Card(
        modifier = modifier
            .clickable { onClickQuote(quote.quoteSeq) },
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Icon(
                painter = painterResource(R.drawable.ic_quote),
                contentDescription = null
            )
            Text(
                text = quote.content,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 5,
                minLines = 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(vertical = 8.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_views),
                    contentDescription = "조회수",
                    modifier = Modifier.padding(end = 2.dp)
                )
                Text(
                    text = quote.numViews.toString(),
                    style = MaterialTheme.typography.labelLarge,
                )

                Spacer(Modifier.weight(1f))

                Icon(
                    painter = painterResource(
                        if (quote.liked) {
                            R.drawable.ic_like_200_fill
                        } else {
                            R.drawable.ic_like_200
                        }
                    ),
                    contentDescription = null,
                    tint =
                        if (quote.liked) {
                            Color.Red
                        } else {
                            Color.Black
                        },
                )
                Text(
                    text = quote.numLikes.toString(),
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }

    }
}

@Previews
@Composable
fun QuoteCardPreview() {
    GlimTheme {
        QuoteCard(
            quote = QuoteSummary(
                quoteSeq = 0L,
                content = "content",
                numViews = 10,
                numLikes = 15,
                liked = true
            )
        ) { }
    }
}