package com.jeongbj.presentation.feature.settings

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.jeongbj.android.extentions.showToast
import com.jeongbj.presentation.common.component.GlimTopbar
import com.jeongbj.presentation.common.notification.rememberNotificationPermissionState
import com.jeongbj.presentation.common.preview.Previews
import com.jeongbj.presentation.feature.settings.component.SettingItem
import com.jeongbj.presentation.feature.settings.component.SettingToggleItem
import com.jeongbj.presentation.feature.settings.dialog.TermsDialog
import com.jeongbj.presentation.theme.GlimTheme

@Composable
fun SettingContent(
    modifier: Modifier = Modifier,
    state: SettingState,
    onAction: (SettingAction) -> Unit,
) {
    val context = LocalContext.current
    var showTermsDialog by remember { mutableStateOf(false) }
    val pushPermissionState = rememberNotificationPermissionState(
        onResult = { granted ->
            if (granted) {
                onAction(SettingAction.OnPushSwitchToggled(true))
            }
        }
    )

    val lockPermissionState = rememberNotificationPermissionState(
        onResult = { granted ->
            if (granted) {
                onAction(SettingAction.OnLockScreenSwitchToggled(true))
            }
        }
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        GlimTopbar(
            title = "설정",
            onBackClick = { onAction(SettingAction.OnBackClicked) }
        )

        Spacer(Modifier.height(16.dp))

        LazyColumn(modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
        ) {
            item {
                SettingToggleItem(
                    title = "자동 로그인",
                    description = "앱 실행 시 자동으로 로그인 합니다",
                    checked = state.settings.autoLoginEnabled,
                    onCheckChanged = { onAction(SettingAction.OnAutoLoginSwitchToggled(it)) }
                )

                Spacer(Modifier.height(32.dp))
            }

            item {
                SettingToggleItem(
                    title = "푸시 알림",
                    description = "글림의 모든 알림을 받을 지 설정합니다",
                    checked = state.settings.pushEnabled,
                    onCheckChanged = {
                        when (it) {
                            true -> {
                                if (!pushPermissionState.isEnabled) {
                                    context.showToast("활성화 하려면 알림 권한이 필요해요")
                                    pushPermissionState.requestPermission()
                                } else {
                                    onAction(SettingAction.OnPushSwitchToggled(true))
                                }
                            }
                            false -> {
                                onAction(SettingAction.OnPushSwitchToggled(false))
                            }
                        }

                    }
                )
                Spacer(Modifier.height(32.dp))
            }

            item {
                SettingToggleItem(
                    title = "잠금화면에서 글림 바로보기",
                    description = "잠금화면에서 스와이프하여 글림을 바로 볼 수 있습니다",
                    checked = state.settings.lockScreenEnabled,
                    onCheckChanged = {
                        when (it) {
                            true -> {
                                if (!lockPermissionState.isEnabled) {
                                    context.showToast("활성화 하려면 알림 권한이 필요해요")
                                    lockPermissionState.requestPermission()
                                } else {
                                    onAction(SettingAction.OnLockScreenSwitchToggled(true))
                                }
                            }
                            else -> {
                                onAction(SettingAction.OnLockScreenSwitchToggled(false))
                            }
                        }
                    }
                )

                Spacer(Modifier.height(32.dp))
            }

            item {
                SettingItem(
                    title = "차단한 글림",
                    onClick = { onAction(SettingAction.OnBlockedGlimClicked) }
                )
                SettingItem(
                    title = "차단한 사용자",
                    onClick = { onAction(SettingAction.OnBlockedUserClicked) }
                )
                Spacer(Modifier.height(32.dp))
            }

            item {
                SettingItem(
                    title = "정책 및 약관",
                    onClick = { showTermsDialog = true }
                )
                Spacer(Modifier.height(32.dp))
            }

            item {
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

    if (showTermsDialog) {
        TermsDialog(
            onDismiss = { showTermsDialog = false },
        )
    }
}

private fun showNotificationDeniedMessage(context: Context) {
    context.showToast("알림 권한이 필요한 기능이에요")
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