package com.jeongbj.presentation.feature.settings.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.jeongbj.presentation.common.component.GlimTopbar
import com.jeongbj.presentation.common.preview.Previews
import com.jeongbj.presentation.feature.settings.component.SettingItem
import com.jeongbj.presentation.theme.GlimTheme

@Composable
fun TermsDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit
) {
    val uriHandler = LocalUriHandler.current
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = true,
            dismissOnClickOutside = false
        )
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .statusBarsPadding()
                .background(Color.White),

        ) {
            GlimTopbar(
                title = "정책 및 약관",
                onBackClick = { onDismiss() }
            )

            Spacer(Modifier.height(16.dp))

            LazyColumn(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                item {
                    SettingItem(
                        title = "개인정보 처리방침",
                        onClick = { uriHandler.openUri("https://glim.kro.kr/privacy") }
                    )
                    SettingItem(
                        title = "아동안전 표준정책",
                        onClick = { uriHandler.openUri("https://glim.kro.kr/child") }
                    )
                    SettingItem(
                        title = "데이터 삭제",
                        onClick = { uriHandler.openUri("https://glim.kro.kr/delete") }
                    )
                    Spacer(Modifier.height(32.dp))
                }
            }
        }
    }

}

@Previews
@Composable
fun TermsContentPreview() {
    GlimTheme() {
        TermsDialog{}
    }
}