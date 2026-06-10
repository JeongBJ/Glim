package com.jeongbj.presentation.feature.post.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.jeongbj.domain.book.model.Book
import com.jeongbj.presentation.R
import com.jeongbj.presentation.theme.GlimColor.LightGray300

@Composable
fun QuoteBookContent(
    modifier: Modifier = Modifier,
    book: Book,
    onBookInfoClick: (String?) -> Unit = {},
) {
    DarkGrayRoundedSurface(modifier = modifier) {
        Row(
            modifier =
                Modifier
                    .padding(16.dp)
                    .clickable { onBookInfoClick(book.isbn13) },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = book.coverUrl,
                contentDescription = null,
                modifier = Modifier.size(40.dp, 56.dp),
                alpha = 0.8f,
                contentScale = ContentScale.FillHeight,
                error = painterResource(R.drawable.ic_image_empty),
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = book.author,
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = 1,
                    color = LightGray300,
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}
