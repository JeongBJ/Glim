package com.jeongbj.presentation.common.component

import android.R.id.message
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jeongbj.presentation.common.preview.Previews


@Composable
fun ConfirmDialog(
    title: String,
    message: String,
    onConfirm: () -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(24.dp),
        containerColor = Color(0xFF1A1A1A),
        title = {
            Text(
                text = title,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Text(
                text = message,
                color = Color(0xFFBDBDBD),
                fontSize = 15.sp,
                lineHeight = 22.sp
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm()
                    onDismiss()
                },
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                )
            ) {
                Text(
                    text = "확인",
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold
                )
            }
        },

        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(
                    text = "취소",
                    color = Color(0xFF9E9E9E)
                )
            }
        }
    )
}

@Previews
@Composable
fun ConfirmDialogPreview() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ConfirmDialog(
            title = "Title 영역",
            message = "Message 영역",
            onConfirm = {},
            onDismiss = {}
        )
    }
}