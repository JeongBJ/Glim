package com.jeongbj.presentation.feature.lock.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jeongbj.presentation.R
import com.jeongbj.presentation.common.component.ActionButton
import com.jeongbj.presentation.feature.post.component.DarkGrayRoundedSurface

@Composable
fun LockScreenButtons(
    modifier: Modifier = Modifier,
    onOpenGlimClicked: () -> Unit,
    onOpenCameraClicked: () -> Unit,
    onUnlocked: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SlideToUnlockButton(
                onUnlocked = onUnlocked,
            )
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DarkGrayRoundedSurface {
                    ActionButton(
                        modifier = Modifier,
                        onClick = onOpenCameraClicked,
                        painter = painterResource(R.drawable.ic_camera),
                        tint = Color.White
                    )
                }

                DarkGrayRoundedSurface {
                    ActionButton(
                        modifier = Modifier,
                        onClick = onOpenGlimClicked,
                        painter = painterResource(R.drawable.ic_book_ribbon),
                        tint = Color.White
                    )
                }
            }
        }
    }
}