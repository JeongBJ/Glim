package com.jeongbj.presentation.feature.glim.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.jeongbj.presentation.R
import com.jeongbj.presentation.common.component.ActionButton
import com.jeongbj.presentation.common.preview.Previews
import com.jeongbj.presentation.theme.DarkThemeScreen

@Composable
fun GlimTextDialog(
    modifier: Modifier = Modifier,
    content: String,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismiss() },

    ) {
        Surface(
            modifier = modifier,
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .background(Color.DarkGray)
                    .padding(bottom = 48.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.End)
                        .padding(bottom = 16.dp),
                    contentAlignment = Alignment.TopEnd
                ) {
                    ActionButton(
                        onClick = { onDismiss() },
                        painter = painterResource(R.drawable.ic_close),
                        tint = Color.White
                    )
                }

                Text(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    text = content,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
            }
        }

    }
}


@Previews
@Composable
fun GlimTextDialogPreview() {
    DarkThemeScreen {
        GlimTextDialog(
            content = "contentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontent",
            onDismiss = {}
        )
    }

}