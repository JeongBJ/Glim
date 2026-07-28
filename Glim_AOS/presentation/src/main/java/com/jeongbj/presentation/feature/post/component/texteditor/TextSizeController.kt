package com.jeongbj.presentation.feature.post.component.texteditor

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.jeongbj.presentation.R
import com.jeongbj.presentation.common.component.ActionButton

@Composable
fun TextSizeController(
    onIncreaseFontSize: () -> Unit,
    onDecreaseFontSize: () -> Unit,
) {
    ActionButton(onClick = onIncreaseFontSize, painter = painterResource(R.drawable.ic_plus))
    ActionButton(onClick = onDecreaseFontSize, painter = painterResource(R.drawable.ic_minus))
}