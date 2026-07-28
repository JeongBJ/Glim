package com.jeongbj.presentation.feature.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jeongbj.presentation.R
import com.jeongbj.presentation.theme.GlimTheme
import com.jeongbj.presentation.theme.LightGray600

@Composable
fun HomeLogoSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.glim_logo_text_black),
            contentDescription = null,
            modifier = Modifier.size(56.dp)
        )
        Text(
            text = "마음에 울림을 주는 글귀를 발견하세요",
            color = LightGray600,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview
@Composable
fun HomeLogoSectionPreview() {
    GlimTheme{
        HomeLogoSection()
    }
}