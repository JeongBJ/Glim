package com.jeongbj.presentation.feature.home.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium.copy(
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Default
        ),
        modifier = Modifier.padding(20.dp),
    )
}