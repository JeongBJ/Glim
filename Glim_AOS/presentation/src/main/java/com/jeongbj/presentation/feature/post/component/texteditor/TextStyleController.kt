package com.jeongbj.presentation.feature.post.component.texteditor

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.jeongbj.android.R
import com.jeongbj.presentation.common.component.ActionButton

@Composable
fun TextStyleController(
    isBold: Boolean,
    isItalic: Boolean,
    onToggleBold: () -> Unit,
    onToggleItalic: () -> Unit
) {
    ActionButton(
        onClick = onToggleBold,
        painter = painterResource(R.drawable.ic_bold),
        tint = if (isBold) Color.Yellow else Color.White
    )

    ActionButton(
        onClick = onToggleItalic,
        painter = painterResource(R.drawable.ic_italic),
        tint = if (isItalic) Color.Yellow else Color.White
    )
}