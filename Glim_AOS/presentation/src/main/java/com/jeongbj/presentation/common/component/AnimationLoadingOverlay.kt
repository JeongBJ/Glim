package com.jeongbj.presentation.common.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.jeongbj.presentation.R
import com.jeongbj.presentation.common.preview.Previews
import com.jeongbj.presentation.theme.GlimTheme

@Composable
fun AnimationLoadingOverlay(
    modifier: Modifier = Modifier
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.anim_loading))
    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable(indication = null, interactionSource = remember { MutableInteractionSource() }) { },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            LottieAnimation(
                modifier = Modifier.size(250.dp),
                composition = composition,
                iterations = Int.MAX_VALUE
            )
            Text(
                text = "이미지 생성중..",
                color = Color.White
            )
        }
    }
}

@Previews
@Composable
fun AnimationLoadingOverlayPreview() {
    GlimTheme {
        AnimationLoadingOverlay()
    }
}