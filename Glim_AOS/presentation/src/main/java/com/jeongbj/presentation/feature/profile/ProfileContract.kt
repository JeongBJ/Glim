package com.jeongbj.presentation.feature.profile

import android.net.Uri
import com.jeongbj.core.common.MultipartImage

data class ProfileState(
    val nickname: String = "",
    val imageUri: Uri? = null,
    val multipartImage: MultipartImage? = null,
    val isButtonEnabled: Boolean = false,
    val isNicknameError: Boolean = false
)

sealed interface ProfileAction {
    data class OnImageSelected(val uri: Uri, val multipartImage: MultipartImage): ProfileAction
    data object OnImageClick: ProfileAction
    data class OnNicknameChanged(val nickname: String): ProfileAction
    data object OnCompleteClick: ProfileAction
}

sealed interface ProfileSideEffect {
    data object NavigateToHome : ProfileSideEffect
    data object OpenGallery : ProfileSideEffect
    data class ShowToast(val message: String) : ProfileSideEffect
}