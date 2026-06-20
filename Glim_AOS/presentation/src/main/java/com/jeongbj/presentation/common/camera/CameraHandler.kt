package com.jeongbj.presentation.common.camera

import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.jeongbj.android.image.createImageUri
import com.jeongbj.android.extentions.hasCameraPermission

@Stable
class CameraHandler(
    val openCamera: (CameraTarget) -> Unit
)

@Composable
fun rememberCameraHandler(
    onCaptured: (CameraTarget, Uri) -> Unit,
    onPermissionDenied: () -> Unit = { }
): CameraHandler {
    val context = LocalContext.current
    var pendingTarget by remember {
        mutableStateOf<CameraTarget?>(null)
    }

    var photoUri by remember { mutableStateOf<Uri?>(null) }

    val cameraLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicture()
        ) { success ->
            val uri = photoUri
            val target = pendingTarget
            pendingTarget = null
            photoUri = null
            if (!success || uri == null || target == null) return@rememberLauncherForActivityResult
            onCaptured(target, uri)
        }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if(!granted) {
            photoUri?.let { context.contentResolver.delete(it, null, null) }
            photoUri = null
            pendingTarget = null
            onPermissionDenied()
            return@rememberLauncherForActivityResult
        }
        photoUri?.let {
            cameraLauncher.launch(it)
        }
    }

    val launchCamera: (CameraTarget) -> Unit = { target ->
        pendingTarget = target
        val newUri = context.createImageUri()
        photoUri = newUri
        if (context.hasCameraPermission()) {
            cameraLauncher.launch(newUri)
        } else {
            permissionLauncher.launch(
                Manifest.permission.CAMERA
            )
        }
    }

    val launchCameraState = rememberUpdatedState(launchCamera)
    return remember {
        CameraHandler(
            openCamera = { target -> launchCameraState.value(target) }
        )
    }
}