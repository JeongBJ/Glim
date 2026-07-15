package com.jeongbj.presentation.feature.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.paging.compose.collectAsLazyPagingItems
import com.jeongbj.android.extentions.showToast
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
            }
        }
    }

    SettingContent(
        state = state,
        onAction = { viewModel.onAction(it) }
    )

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