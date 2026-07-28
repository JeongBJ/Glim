package com.jeongbj.presentation.feature.book.detail.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jeongbj.presentation.feature.book.detail.BookDetailAction
import com.jeongbj.presentation.feature.book.detail.BookDetailState
import com.jeongbj.presentation.theme.GlimColor.LightBrown

@Composable
fun BoxScope.BookDetailButtons(
    state: BookDetailState,
    onAction: (BookDetailAction) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .align(Alignment.BottomEnd),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedButton(
            onClick = { onAction(BookDetailAction.OnBuyBookClick(state.book.linkUrl ?: "")) },
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.White,
                contentColor = LightBrown
            ),
            border = BorderStroke(1.dp, LightBrown),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "책 구매",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp)
            )
        }

        Button(
            onClick = { onAction(BookDetailAction.OnRegisterQuoteClick(state.book)) },
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = LightBrown,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "글귀 등록",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}