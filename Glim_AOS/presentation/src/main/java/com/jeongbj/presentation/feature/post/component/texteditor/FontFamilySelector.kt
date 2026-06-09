package com.jeongbj.presentation.feature.post.component.texteditor

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jeongbj.presentation.feature.post.component.DarkGrayRoundedSurface
import com.jeongbj.presentation.theme.GlimFonts

@Composable
fun FontFamilySelector(selectedFontFamily: (FontFamily) -> Unit) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(GlimFonts.entries) {
            DarkGrayRoundedSurface(
                modifier = Modifier.clickable { selectedFontFamily(it.fontFamily) }
            ) {
                Text(
                    text = "가나다라",
                    style = MaterialTheme.typography.bodySmall.copy(fontFamily = it.fontFamily),
                    modifier = Modifier.padding(8.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}