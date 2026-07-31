package com.jeongbj.presentation.feature.post

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.jeongbj.android.extentions.showToast
import com.jeongbj.presentation.common.camera.CameraTarget
import com.jeongbj.presentation.common.camera.rememberCameraHandler
import com.jeongbj.presentation.common.component.AnimationLoadingOverlay
import com.jeongbj.presentation.common.component.ConfirmDialog
import com.jeongbj.presentation.common.component.LoadingOverlay
import com.jeongbj.presentation.feature.post.ocr.TextRecognizeContent
import com.jeongbj.presentation.feature.post.viewmodel.PostViewModel
import com.jeongbj.presentation.theme.DarkThemeScreen

@Composable
fun PostScreen(
    viewModel: PostViewModel = hiltViewModel(),
    navigateToInfo: () -> Unit,
    navigateBack: () -> Unit,
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    val backgroundImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let { viewModel.onAction(PostAction.OnBackgroundImageSelected(uri)) }
    }

    val cameraLauncher = rememberCameraHandler(
        onCaptured = { target, uri ->
            when (target) {
                CameraTarget.BACKGROUND -> {
                    viewModel.onAction(PostAction.OnBackgroundImageSelected(uri))
                }

                CameraTarget.OCR -> {
                    viewModel.onAction(PostAction.OnTextImageSelected(uri))
                }

                else -> {}
            }
        },
        onPermissionDenied = {
            context.showToast("카메라 권한이 필요합니다.")
        }
    )

    var showCloseDialog by remember { mutableStateOf(false) }

    BackHandler {
        showCloseDialog = true
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is PostSideEffect.OpenCamera -> {
                    cameraLauncher.openCamera(effect.cameraTarget)
                }

                PostSideEffect.OpenGallery -> {
                    backgroundImageLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }

                PostSideEffect.ShowCloseDialog -> {
                    showCloseDialog = true
                }

                is PostSideEffect.ShowToast -> {
                    context.showToast(effect.msg)
                }

                PostSideEffect.NavigateBack -> navigateBack()
                PostSideEffect.NavigateToInfo -> navigateToInfo()
            }
        }
    }

    if (showCloseDialog) {
        ConfirmDialog(
            title = "종료",
            message = "작업 중인 내용은 저장되지 않습니다.\n종료하시겠습니까?",
            onConfirm = {
                showCloseDialog = false
                if (state.ocrImageUri == null) {
                    navigateBack()
                } else {
                    viewModel.onAction(PostAction.OnTextImageSelected(null))
                }
            },
            onDismiss = { showCloseDialog = false }
        )
    }

    DarkThemeScreen {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            if (state.ocrImageUri != null) {
                TextRecognizeContent(
                    modifier = Modifier.statusBarsPadding(),
                    imageUri = state.ocrImageUri,
                    onCompleteClicked = { viewModel.onAction(PostAction.OnTextRecognized(it)) },
                    onCloseClicked = { viewModel.onAction(PostAction.OnTextImageSelected(null)) }

                )
            } else {
                PostContent(
                    state = state,
                    onAction = { viewModel.onAction(it) },
                    onCapture = { viewModel.onCaptured(it) }
                )
            }

            if (state.isLoading) {
                LoadingOverlay()
            }

            if (state.isGenerating) {
                AnimationLoadingOverlay()
            }
        }
    }

}