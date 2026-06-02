package com.jeongbj.presentation.feature.book.search.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.jeongbj.domain.book.model.Book
import com.jeongbj.presentation.common.component.GlimAsyncImage
import com.jeongbj.presentation.common.preview.Previews
import com.jeongbj.presentation.feature.book.search.SearchAction
import com.jeongbj.presentation.theme.GlimTheme

fun LazyListScope.bookResultSection(
    books: LazyPagingItems<Book>,
    onAction: (SearchAction) -> Unit,
) {
    items(
        count = books.itemCount,
        key = books.itemKey {
            it.isbn13.ifBlank {
                "${it.title}_${it.author}"
            }
        }
    ) { index ->
        books[index]?.let { book ->
            BookItem(
                modifier = Modifier.padding(16.dp),
                book = book,
                onAction = {
                    onAction(SearchAction.OnBookClick(book.isbn13))
                }

            )
        }
    }
}

@Composable
fun BookItem(
    book: Book,
    onAction: (SearchAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable{ onAction(SearchAction.OnBookClick(book.isbn13)) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            GlimAsyncImage(
                imageUrl = book.coverUrl,
                modifier = Modifier
                    .size(width = 80.dp, height = 120.dp)
                    .clip(RoundedCornerShape(4.dp))
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "${book.author} • ${book.publisher} • ${book.pubDate}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = book.description ?: "",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Black,
                    lineHeight = 20.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Previews
@Composable
private fun BookItemPreview() {
    GlimTheme {
        BookItem(
            book = Book(
                title = "title",
                author = "author",
                coverUrl = "",
                isbn13 = "",
            ),
            onAction = { }
        )
    }
}