package com.jeongbj.presentation.feature.post.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jeongbj.domain.book.model.Book
import com.jeongbj.presentation.R

@Composable
fun BookInfoSection(
    modifier: Modifier = Modifier,
    book: Book? = null,
    onAddBookInfoClicked: () -> Unit,
) {
    if (book == null) {
        DarkGrayRoundedSurface(modifier = modifier.padding(vertical = 4.dp)) {
            Row(
                modifier =
                    modifier
                        .padding(8.dp).padding(end = 16.dp)
                        .clickable { onAddBookInfoClicked() },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = { onAddBookInfoClicked() }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_post),
                        contentDescription = null,
                    )
                }
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "책 정보 추가",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    } else {
        QuoteBookContent(
            modifier = modifier.padding(vertical = 8.dp),
            book = book,
            onBookInfoClick = { onAddBookInfoClicked() }
        )
    }

}