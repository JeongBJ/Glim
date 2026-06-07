package com.jeongbj.presentation.feature.post

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.jeongbj.presentation.common.camera.CameraTarget
import com.jeongbj.presentation.common.camera.rememberCameraHandler
import com.jeongbj.presentation.feature.post.viewmodel.PostViewModel

@Composable
fun PostScreen(
    viewModel: PostViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    val backgroundImageLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { viewModel.onAction(PostAction.OnBackgroundImageSelected(uri)) }
    }

    val textImageLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { viewModel.onAction(PostAction.OnTextImageSelected(uri)) }
    }

    val cameraLauncher = rememberCameraHandler(
        onCaptured = { target, uri ->
            when (target) {
                CameraTarget.BACKGROUND -> { }
                CameraTarget.OCR -> { }
                else -> { }
            }
        },
        onPermissionDenied = { Toast.makeText(context, "카메라 권한이 필요합니다.", Toast.LENGTH_SHORT).show() }
    )

    Box(
        modifier = Modifier.fillMaxSize()
            .statusBarsPadding()
    ) {
        PostContent(
            state = state,
            onAction = { viewModel.onAction(it) }
        )
    }
    
}