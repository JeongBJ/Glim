package com.jeongbj.presentation.common.component

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    painter: Painter,
    contentDescription: String? = null,
    enabled: Boolean = true,
    tint: Color = LocalContentColor.current
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
    ) {
        Icon(
            painter = painter,
            contentDescription = contentDescription,
            tint = tint
        )
    }
}