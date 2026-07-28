package com.jeongbj.presentation.feature.post.ocr

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.jeongbj.presentation.R
import com.jeongbj.presentation.feature.post.component.DarkGrayRoundedSurface

@Composable
fun TextRecognizeHeaderSection(
    modifier: Modifier = Modifier,
    onCloseClicked: () -> Unit,
    onCompleteClicked: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        DarkGrayRoundedSurface {
            IconButton(
                onClick = onCloseClicked
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_close),
                    contentDescription = null
                )
            }
        }


        Spacer(modifier = Modifier.weight(1f))

        DarkGrayRoundedSurface {
            TextButton(
                onClick = onCompleteClicked
            ) {
                Text("완료", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}