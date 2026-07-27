package com.jeongbj.presentation.feature.settings

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import android.provider.Settings
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.paging.compose.collectAsLazyPagingItems
import com.jeongbj.android.extentions.showToast
import com.jeongbj.presentation.common.component.ConfirmDialog
import com.jeongbj.presentation.common.preview.Previews
import com.jeongbj.presentation.feature.settings.dialog.BlockListDialog
import com.jeongbj.presentation.feature.settings.dialog.BlockedGlimContent
import com.jeongbj.presentation.feature.settings.dialog.BlockedUserContent
import com.jeongbj.presentation.feature.settings.viewmodel.SettingViewModel
import com.jeongbj.presentation.theme.GlimTheme

@Composable
fun SettingScreen(
    viewModel: SettingViewModel,
    navigateBack: () -> Unit,
    navigateToLogin: () -> Unit,
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    var showBlockedQuotes by remember { mutableStateOf(false) }
    var showBlockedUsers by remember { mutableStateOf(false) }
    var showLockScreenConfirmDialog by remember { mutableStateOf(false) }
    val blockedUsers = viewModel.blockedUsers.collectAsLazyPagingItems()
    val blockedQuotes = viewModel.blockedQuotes.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                SettingSideEffect.NavigateBack -> navigateBack()
                SettingSideEffect.NavigateLogin -> navigateToLogin()
                is SettingSideEffect.ShowToast -> context.showToast(effect.message)
                SettingSideEffect.ShowBlockedGlim -> showBlockedQuotes = true
                SettingSideEffect.ShowBlockedUser -> showBlockedUsers = true
                SettingSideEffect.ShowLockScreenDialog -> showLockScreenConfirmDialog = true
                SettingSideEffect.OpenBatterySetting -> requestIgnoreBatteryOptimizations(context)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()){

        Column {

            SettingContent(
                state = state,
                onAction = { viewModel.onAction(it) }
            )
        }
    }


    if (showBlockedUsers) {
        BlockListDialog(
            onDismiss = { showBlockedUsers = false },
            content = {
                BlockedUserContent(
                    users = blockedUsers,
                    onUnblockUserClicked = { viewModel.onAction(SettingAction.OnUnblockUserClicked(it)) }
                )
            }
        )
    }

    if (showBlockedQuotes) {
        BlockListDialog(
            onDismiss = { showBlockedQuotes = false },
            content = {
                BlockedGlimContent(
                    onUnblockGlimClicked = { viewModel.onAction(SettingAction.OnUnblockQuoteClicked(it)) },
                    quotes = blockedQuotes
                )
            }
        )
    }

    if (showLockScreenConfirmDialog) {
        ConfirmDialog(
            title = "잠금화면에서 글림 바로보기",
            message = "기본 잠금화면 위에 글림이 표시됩니다\n항상 백그라운드에서 실행되어 배터리 사용량이 많아집니다\n설정 하시겠어요?",
            onConfirm = {
                viewModel.onAction(SettingAction.OnLockScreenConfirmClicked)
                showLockScreenConfirmDialog = false
                        },
            onDismiss = { showLockScreenConfirmDialog = false }
        )
    }
}

@SuppressLint("BatteryLife")
fun requestIgnoreBatteryOptimizations(context: Context) {
    val packageName = context.packageName
    val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager

    if (!powerManager.isIgnoringBatteryOptimizations(packageName)) {
        val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply {
            data = "package:$packageName".toUri()
        }
        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            openAppSettingsFallback(context)
        }
    } else {
        context.showToast("잠금화면에서 바로보기가 설정되었습니다")
    }
}

private fun openAppSettingsFallback(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = "package:${context.packageName}".toUri()
    }
    context.startActivity(intent)
}

@Previews
@Composable
fun SettingScreenPreview() {
    GlimTheme {
        SettingContent(
            state = SettingState(),
            onAction = { }
        )
    }
}