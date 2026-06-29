package com.jeongbj.presentation.feature.profile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jeongbj.presentation.common.preview.Previews
import com.jeongbj.presentation.common.util.toMultipartImage

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onNavigateHome: () -> Unit,
    popBackStack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri ?: return@rememberLauncherForActivityResult
        val image = uri.toMultipartImage(context)
        viewModel.onAction(ProfileAction.OnImageSelected(uri, image))
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is ProfileSideEffect.OpenGallery -> {
                    launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }

                is ProfileSideEffect.NavigateToHome -> {
                    onNavigateHome()
                }

                is ProfileSideEffect.ShowToast -> {

                }

                ProfileSideEffect.PopBackStack -> popBackStack()
            }
        }
    }

    ProfileContent(
        state = state,
        onAction = { action ->
            viewModel.onAction(action)
        }
    )
}



@Previews
@Composable
fun ProfileScreenPreview() {
    ProfileContent(
        state = ProfileState(nickname = "닉네임"),
        onAction = {}
    )
}

