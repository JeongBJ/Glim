package com.jeongbj.presentation.common.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.jeongbj.presentation.R
import com.jeongbj.presentation.common.preview.Previews
import com.jeongbj.presentation.theme.GlimTheme

@Composable
fun GlimTopbar(
    title: String? = null,
    onBackClick: () -> Unit
) {
    val showText = !title.isNullOrBlank()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_back),
                contentDescription = "뒤로가기"
            )
        }
        if(showText) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(end = 16.dp)
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.glim_logo_text_black),
                contentDescription = null,
                modifier = Modifier.size(64.dp)
                    .align(Alignment.Center)
            )
        }
    }

}

@Previews
@Composable
fun GlimTopbarPreview() {
    GlimTheme {
        GlimTopbar(
            title = "title", onBackClick = {}
        )
    }
}