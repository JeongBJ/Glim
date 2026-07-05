package com.jeongbj.presentation.feature.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jeongbj.presentation.common.component.GlimTopbar
import com.jeongbj.presentation.common.preview.Previews
import com.jeongbj.presentation.feature.settings.component.SettingItem
import com.jeongbj.presentation.feature.settings.component.SettingToggleItem
import com.jeongbj.presentation.theme.GlimTheme

@Composable
fun SettingContent(
    modifier: Modifier = Modifier,
    state: SettingState,
    onAction: (SettingAction) -> Unit
) {

    Column(
        modifier = modifier.fillMaxSize()
            .statusBarsPadding()
    ) {
        GlimTopbar(
            title = "설정",
            onBackClick = { onAction(SettingAction.OnBackClicked) }
        )

        Spacer(Modifier.height(16.dp))

        Column(modifier = modifier.fillMaxWidth()
            .padding(horizontal = 16.dp)
        ) {
            SettingToggleItem(
                title = "자동 로그인",
                description = "앱 실행 시 자동으로 로그인 합니다",
                checked = state.settings.autoLoginEnabled,
                onCheckChanged = { onAction(SettingAction.OnAutoLoginSwitchToggled(it)) }
            )

            Spacer(Modifier.height(32.dp))
            SettingToggleItem(
                title = "푸시 알림",
                description = "글림의 모든 알림을 받을 지 설정합니다",
                checked = state.settings.pushEnabled,
                onCheckChanged = { onAction(SettingAction.OnPushSwitchToggled(it)) }
            )

            Spacer(Modifier.height(32.dp))

            SettingToggleItem(
                title = "잠금화면에서 글림 바로보기",
                description = "잠금화면에서 스와이프하여 글림을 바로 볼 수 있습니다",
                checked = state.settings.lockScreenEnabled,
                onCheckChanged = { onAction(SettingAction.OnLockScreenSwitchToggled(it)) }
            )

            Spacer(Modifier.height(32.dp))

            SettingItem(
                title = "로그아웃",
                onClick = { onAction(SettingAction.OnLogoutClicked) }
            )


            SettingItem(
                title = "회원 탈퇴",
                onClick = { onAction(SettingAction.OnResignClicked) }
            )
        }
    }
}

@Previews
@Composable
fun SettingContentPreview() {
    GlimTheme {
        SettingContent(
            state = SettingState(),
            onAction = {}
        )
    }
}