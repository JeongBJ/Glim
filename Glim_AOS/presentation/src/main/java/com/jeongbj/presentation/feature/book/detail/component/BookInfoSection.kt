package com.jeongbj.presentation.feature.book.detail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jeongbj.presentation.common.preview.Previews
import com.jeongbj.presentation.feature.book.detail.BookDetailState
import com.jeongbj.presentation.theme.GlimTheme

@Composable
fun BookInfoSection(
    state: BookDetailState,
    modifier: Modifier = Modifier
) {
    val book = state.book
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Surface(
            modifier = modifier
                .padding(vertical = 8.dp),
            color = Color.LightGray.copy(alpha = 0.3f),
            shape = RoundedCornerShape(9.dp)
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = "글귀 ${state.book.quotes.size}개",
                style = MaterialTheme.typography.labelMedium,
            )
        }

        Text(
            text = book.title,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = book.category ?: "",
            style = MaterialTheme.typography.bodySmall,
        )

        Text(
            text = book.author,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(end = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = book.publisher ?: "",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .weight(1f)
            )

            Text(
                text = book.pubDate.toString(),
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(Modifier.height(8.dp))

        Text(
            text = "가격 ${book.priceSales}",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.End)
        )

    }

}

@Previews
@Composable
fun BookInfoSectionPreview() {
    GlimTheme {
        BookInfoSection(
            state = BookDetailState(),
        )
    }
}