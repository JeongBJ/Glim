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
import com.jeongbj.presentation.common.component.ConfirmDialog
import com.jeongbj.presentation.common.preview.Previews
import com.jeongbj.presentation.feature.login.util.GoogleLoginLauncher
import com.jeongbj.presentation.feature.login.util.GoogleLoginResult
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateHome: () -> Unit,
    googleClientId: String
) {
    var showNoCredentialDialog by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val googleLoginLauncher = remember { GoogleLoginLauncher(context) }
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
                            viewModel.googleLogin(result.idToken)
                        }

                        GoogleLoginResult.NoCredential -> {
                            showNoCredentialDialog = true
                        }

                        else -> { }
                    }

                }
            }

            else -> viewModel.onClickEvent(event)
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
        viewModel.event.collect { event ->
            if (event is LoginEvent.LoginSuccess) {
                onNavigateHome()
            }
        }
    }

    LoginContent(
        onEvent = onClickEvent
    )
}


@Previews
@Composable
fun LoginScreenPreview() {
    LoginContent(
        onEvent = {}
    )
}