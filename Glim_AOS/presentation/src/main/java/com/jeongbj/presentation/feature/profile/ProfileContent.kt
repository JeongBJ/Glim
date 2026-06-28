package com.jeongbj.presentation.feature.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jeongbj.presentation.common.preview.Previews
import com.jeongbj.presentation.feature.profile.component.ProfileImageSection
import com.jeongbj.presentation.feature.profile.component.ProfileInputSection

@Composable
fun ProfileContent(state: ProfileState, onAction: (ProfileAction) -> Unit) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A0A1A))
    ) {
        if (maxWidth < 600.dp) {
            ProfileSetupPortrait(state, onAction)
        } else {
            ProfileSetupLandscape(state, onAction)
        }
    }
}

@Composable
private fun ProfileSetupPortrait(
    state: ProfileState, onAction: (ProfileAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        ProfileImageSection(
            imageModel = state.imageUri,
            onImageClick = { onAction(ProfileAction.OnImageClick) }
        )

        Spacer(modifier = Modifier.height(32.dp))

        ProfileInputSection(
            nickname = state.nickname,
            onNicknameChange = { onAction(ProfileAction.OnNicknameChanged(it)) },
            onCompleteClick = { onAction(ProfileAction.OnCompleteClick) },
            isButtonEnabled = state.isButtonEnabled,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun ProfileSetupLandscape(
    state: ProfileState, onAction: (ProfileAction) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(32.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            ProfileImageSection(
                imageModel = state.imageUri,
                onImageClick = { onAction(ProfileAction.OnImageClick) }
            )
        }

        Column(
            modifier = Modifier
                .weight(1.2f)
                .padding(40.dp),
            verticalArrangement = Arrangement.Center
        ) {
            ProfileInputSection(
                nickname = state.nickname,
                onNicknameChange = { onAction(ProfileAction.OnNicknameChanged(it)) },
                onCompleteClick = { onAction(ProfileAction.OnCompleteClick) },
                isButtonEnabled = state.isButtonEnabled
            )
        }
    }
}

@Previews
@Composable
fun ProfileContentPreview() {
    ProfileContent(
        state = ProfileState(),
        onAction = {}
    )
}