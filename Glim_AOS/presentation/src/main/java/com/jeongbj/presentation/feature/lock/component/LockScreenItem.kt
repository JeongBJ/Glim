package com.jeongbj.presentation.feature.lock.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jeongbj.domain.quote.model.QuoteThumbnail
import com.jeongbj.presentation.R
import com.jeongbj.presentation.common.component.ActionButton
import com.jeongbj.presentation.common.component.GlimAsyncImage
import com.jeongbj.presentation.feature.post.component.DarkGrayRoundedSurface

@Composable
fun LockScreenItem(
    modifier: Modifier = Modifier,
    quote : QuoteThumbnail,
    onSaveClicked: (String) -> Unit
){
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DarkGrayRoundedSurface {
                ActionButton(
                    modifier = Modifier,
                    onClick = { quote.imageUrl?.let { onSaveClicked(it) } },
                    painter = painterResource(R.drawable.ic_download),
                    tint = Color.White
                )
            }
        }
        Box(
            modifier = modifier
                .aspectRatio(4/7f)
                .align(Alignment.Center)
        ) {
            GlimAsyncImage(
                imageUrl = quote.imageUrl
            )
        }
    }
}