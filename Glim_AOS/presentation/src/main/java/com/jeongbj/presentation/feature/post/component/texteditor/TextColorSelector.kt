package com.jeongbj.presentation.feature.post.component.texteditor

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jeongbj.presentation.theme.postFontColorList

@Composable
fun TextColorSelector(selectedColor: (Color) -> Unit) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(postFontColorList) {
            Surface(
                modifier = Modifier
                    .clickable { selectedColor(it) },
                color = Color.White.copy(alpha = 0.9f),
            ) {
                Box(
                    Modifier
                        .size(24.dp)
                        .padding(2.dp)
                        .background(it)
                )
            }
        }
    }
}