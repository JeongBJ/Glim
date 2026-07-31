package com.jeongbj.presentation.feature.glim.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jeongbj.domain.book.model.Book
import com.jeongbj.domain.quote.model.Quote
import com.jeongbj.domain.user.model.User
import com.jeongbj.presentation.R
import com.jeongbj.presentation.common.component.ActionButton
import com.jeongbj.presentation.common.component.GlimAsyncImage
import com.jeongbj.presentation.common.preview.Previews
import com.jeongbj.presentation.feature.post.component.QuoteBookContent
import com.jeongbj.presentation.theme.DarkThemeScreen

@Composable
fun GlimItem(
    modifier: Modifier = Modifier,
    quote: Quote,
    isOwner: Boolean = false,
    onLikeClicked: () -> Unit = {},
    onShareClicked: () -> Unit = {},
    onBookInfoClicked: (String) -> Unit = {},
    onSaveClicked: (String) -> Unit = {},
    onProfileClicked: (Long) -> Unit = {},
    onDeleteClicked: (Long) -> Unit = {},
    onBlockQuoteClicked: (Long) -> Unit = {},
    onBlockUserClicked: (Long) -> Unit = {}
) {
    var showTextDialog by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
    ) {
        GlimAsyncImage(
            modifier = Modifier
                .aspectRatio(4 / 7f)
                .align(Alignment.Center),
            imageUrl = quote.imageUrl
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.End
        ) {

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                GlimProfile(
                    imageModel = quote.user.imageUrl,
                    nickname = quote.user.nickname,
                    onProfileClicked = { onProfileClicked(quote.user.userSeq) }
                )

                GlimPopupMenu(
                    isOwner = isOwner,
                    onDeleteClicked = { onDeleteClicked(quote.quoteSeq) },
                    onBlockUserClicked = { onBlockUserClicked(quote.user.userSeq) },
                    onBlockQuoteClicked = { onBlockQuoteClicked(quote.quoteSeq) }
                )
            }
            ActionButton(
                onClick = { onSaveClicked(quote.imageUrl) },
                painter = painterResource(R.drawable.ic_download)
            )

            Spacer(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier.padding(horizontal = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ActionButton(
                    onClick = onLikeClicked,
                    painter = painterResource(
                        if (quote.liked) R.drawable.ic_like_200_fill
                        else R.drawable.ic_like_200
                    ),
                    tint = if (quote.liked) Color.Red else Color.White
                )

                Text(
                    text = "${quote.numLikes}",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )

                ActionButton(
                    onClick = onShareClicked,
                    painter = painterResource(R.drawable.ic_share)
                )

                ActionButton(
                    onClick = { showTextDialog = true },
                    painter = painterResource(R.drawable.ic_text)
                )
            }

            Spacer(Modifier.height(16.dp))

            QuoteBookContent(
                book = quote.book,
                onBookInfoClick = { onBookInfoClicked(quote.book.isbn13) }
            )

        }

        if (showTextDialog) {
            GlimTextDialog(
                content = quote.content,
                onDismiss = { showTextDialog = false }
            )
        }
    }
}

@Composable
@Previews
fun GlimItemPreview() {
    DarkThemeScreen {
        GlimItem(
            quote = Quote(
                quoteSeq = 0,
                imageUrl = "",
                content = "content",
                numLikes = 10,
                liked = true,
                user = User(
                    userSeq = 0,
                    nickname = "nickname"
                ),
                book = Book(
                    title = "title",
                    coverUrl = "",
                    author = "author",
                    isbn13 = ""
                )
            )
        )
    }
}