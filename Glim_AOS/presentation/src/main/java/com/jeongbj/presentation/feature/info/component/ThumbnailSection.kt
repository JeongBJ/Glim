package com.jeongbj.presentation.feature.info.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.jeongbj.domain.quote.model.QuoteThumbnail
import com.jeongbj.presentation.common.component.GlimAsyncImage

@Composable
fun ThumbnailItem(
    quote: QuoteThumbnail,
    modifier: Modifier = Modifier,
    onClicked: (Long) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .aspectRatio(3/4f)
            .clickable(onClick = { onClicked(quote.quoteSeq) })
    ) {
        GlimAsyncImage(
            imageUrl = quote.imageUrl,
            contentScale = ContentScale.Crop
        )
    }
}