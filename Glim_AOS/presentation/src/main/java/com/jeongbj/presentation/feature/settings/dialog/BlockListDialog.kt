package com.jeongbj.presentation.feature.settings.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import com.jeongbj.domain.quote.model.QuoteThumbnail
import com.jeongbj.domain.user.model.User
import com.jeongbj.presentation.R
import com.jeongbj.presentation.common.component.ActionButton
import com.jeongbj.presentation.common.component.GlimAsyncImage
import com.jeongbj.presentation.common.preview.Previews
import com.jeongbj.presentation.feature.post.component.DarkGrayRoundedSurface
import com.jeongbj.presentation.theme.GlimTheme
import kotlinx.coroutines.flow.flowOf

@Composable
fun BlockListDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
        )
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Surface(
                modifier = modifier
                    .widthIn(max = 600.dp)
                    .fillMaxWidth(0.9f),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(

                ) {
                    ActionButton(
                        modifier = Modifier.padding(16.dp),
                        onClick = { onDismiss() },
                        painter = painterResource(R.drawable.ic_close),
                    )
                    content()
                }
            }
        }
    }
}

@Composable
fun BlockedUserContent(
    modifier: Modifier = Modifier,
    onUnblockUserClicked: (Long) -> Unit,
    users: LazyPagingItems<User>,
) {
    if (users.itemCount == 0) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = 40.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "차단한 사용자가 없습니다.")
        }
        return
    }
    LazyColumn(
        modifier = modifier.padding(32.dp)
    ) {
        items(users.itemCount) { index ->
            users[index]?.let { user ->
                BlockedUserItem(
                    user = user,
                    onUnblockUserClicked = { onUnblockUserClicked(user.userSeq) }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun BlockedGlimContent(
    modifier: Modifier = Modifier,
    onUnblockGlimClicked: (Long) -> Unit,
    quotes: LazyPagingItems<QuoteThumbnail>,
) {
    if (quotes.itemCount == 0) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = 40.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "차단한 글림이 없습니다.")
        }
        return
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
    ) {
        items(quotes.itemCount) {
            quotes[it]?.let { quote ->
                BlockedGlimItem(
                    modifier = modifier.padding(8.dp),
                    quote = quote,
                    onUnblockGlimClicked = { onUnblockGlimClicked(quote.quoteSeq) }
                )
            }
        }
    }
}

@Composable
fun BlockedGlimItem(
    modifier : Modifier = Modifier,
    quote: QuoteThumbnail,
    onUnblockGlimClicked: (Long) -> Unit,
) {
    Column (
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(8/15f),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            ),
        ) {
            GlimAsyncImage(
                modifier = Modifier.padding(16.dp)
                    .aspectRatio(4/7f),
                imageUrl = quote.imageUrl
            )
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                DarkGrayRoundedSurface {
                    TextButton(
                        onClick = { onUnblockGlimClicked(quote.quoteSeq) },
                    ) {
                        Text(
                            text = "차단 해제",
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun BlockedUserItem(
    modifier: Modifier = Modifier,
    user: User,
    onUnblockUserClicked: (Long) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        DarkGrayRoundedSurface {
            Row(
                modifier = modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = user.imageUrl,
                    contentDescription = "프로필 이미지",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape),
                    error = painterResource(R.drawable.img_empty_profile)
                )
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = user.nickname,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        DarkGrayRoundedSurface {
            TextButton(
                onClick = { onUnblockUserClicked(user.userSeq) },
            ) {
                Text(
                    text = "차단 해제",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }
    }
}

@Previews
@Composable
fun BlockedUserContentPreview() {
    GlimTheme {
        BlockListDialog(
            onDismiss = {},
            content = {
                BlockedGlimContent(
                    onUnblockGlimClicked = { },
                    quotes = remember {
                        flowOf(
                            PagingData.from(
                                listOf(
                                    QuoteThumbnail(
                                        imageUrl = "https://picsum.photos/200",
                                        quoteSeq = 0
                                    ),
                                    QuoteThumbnail(
                                        imageUrl = "https://picsum.photos/200",
                                        quoteSeq = 0
                                    ),
                                    QuoteThumbnail(
                                        imageUrl = "https://picsum.photos/200",
                                        quoteSeq = 0
                                    ),
                                    QuoteThumbnail(
                                        imageUrl = "https://picsum.photos/200",
                                        quoteSeq = 0
                                    ),
                                    QuoteThumbnail(
                                        imageUrl = "https://picsum.photos/200",
                                        quoteSeq = 0
                                    ),
                                )

                            )
                        )
                    }.collectAsLazyPagingItems()
                )
            }
        )
    }


}
