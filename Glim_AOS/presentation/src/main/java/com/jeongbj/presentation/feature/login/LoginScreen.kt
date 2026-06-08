package com.jeongbj.presentation.feature.login

import android.content.Intent
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jeongbj.presentation.common.component.ConfirmDialog
import com.jeongbj.presentation.common.preview.Previews
import com.jeongbj.presentation.feature.login.util.GoogleLoginLauncher
import com.jeongbj.presentation.feature.login.util.GoogleLoginResult
import com.jeongbj.presentation.feature.login.util.KakaoLoginLauncher
import com.jeongbj.presentation.feature.login.util.KakaoLoginResult
import com.jeongbj.presentation.theme.DarkThemeScreen
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    googleClientId: String,
    onNavigateHome: () -> Unit,
    onNavigateProfile: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var showNoCredentialDialog by remember {
        mutableStateOf(false)
    }

    val googleLoginLauncher = remember(context) { GoogleLoginLauncher(context) }
    val kakaoLoginLauncher = remember(context) { KakaoLoginLauncher(context) }
    val openGoogleAccountSetting = remember(context) {
        {
            context.startActivity(
                Intent(Settings.ACTION_ADD_ACCOUNT).apply {
                    putExtra(
                        Settings.EXTRA_ACCOUNT_TYPES,
                        arrayOf("com.google")
                    )
                }
            )
        }
    }

    val onClickEvent: (LoginEvent) -> Unit = { event ->
        when (event) {
            LoginEvent.OnGoogleClick -> {
                scope.launch {
                    when (val result = googleLoginLauncher.login(googleClientId)) {
                        is GoogleLoginResult.Success -> {
                            viewModel.onClickEvent(event, result.idToken)
                        }

                        GoogleLoginResult.NoCredential -> {
                            showNoCredentialDialog = true
                        }
                        else -> { }
                    }

                }
            }

            LoginEvent.OnKakaoClick -> {
                scope.launch {
                    when (val result = kakaoLoginLauncher.login()) {
                        is KakaoLoginResult.Success -> {
                            viewModel.onClickEvent(event, result.idToken)
                        }
                        else -> { }
                    }
                }
            }

        }
    }


    if (showNoCredentialDialog) {
        ConfirmDialog(
            "Google 계정 없음",
            "추가하시겠습니까?",
            onConfirm = {
                showNoCredentialDialog = false
                openGoogleAccountSetting()
            },
            onDismiss = { showNoCredentialDialog = false }
        )
    }

    LaunchedEffect(Unit) {
        viewModel.loginEffect.collect { effect ->
            when (effect) {
                is LoginEffect.NavigateHome -> {
                    onNavigateHome()
                }

                is LoginEffect.NavigateProfile -> {
                    onNavigateProfile()
                }

                else -> Unit
            }
        }
    }

    DarkThemeScreen {
        LoginContent(
            isLoading = uiState.isLoading,
            onEvent = onClickEvent
        )
    }
}


@Previews
@Composable
fun LoginScreenPreview() {
    LoginContent(
        onEvent = {},
        false
    )
}