package com.jeongbj.presentation.feature.book.detail.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jeongbj.presentation.R
import com.jeongbj.presentation.common.preview.Previews
import com.jeongbj.presentation.feature.book.detail.BookDetailAction
import com.jeongbj.presentation.theme.GlimTheme

@Composable
fun ContentsSection(
    title: String,
    modifier: Modifier = Modifier,
    isExpanded: Boolean = false,
    onAction: (BookDetailAction) -> Unit = { }
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable { onAction(BookDetailAction.ToggleBookDescriptionExpanded) },
        horizontalArrangement = Arrangement.SpaceBetween

    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )

        Icon(
            painter = painterResource(id = if (isExpanded) R.drawable.ic_arrow_down else R.drawable.ic_forward),
            contentDescription = "더보기",
            tint = MaterialTheme.colorScheme.primary,
        )
    }

}

@Previews
@Composable
fun ContentsSectionPreview() {
    GlimTheme {
        ContentsSection(
            title = "title",
            isExpanded = true
        )
    }
}
