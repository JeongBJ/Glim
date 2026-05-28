package com.jeongbj.presentation.feature.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeongbj.core.common.MultipartImage
import com.jeongbj.core.common.ResultType
import com.jeongbj.domain.user.model.User
import com.jeongbj.domain.user.usecase.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userUseCases: UserUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<ProfileSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()


    fun onAction(action: ProfileAction) {
        when (action) {
            is ProfileAction.OnNicknameChanged -> onNicknameChanged(action.nickname)
            is ProfileAction.OnImageSelected -> onImageSelected(action.uri, action.multipartImage)
            is ProfileAction.OnImageClick -> onImageClick()
            is ProfileAction.OnCompleteClick -> onCompleteClick()
        }
    }

    private fun onNicknameChanged(nickname: String) {
        val isValid = isNicknameValid(nickname)
        _state.update {
            it.copy(
                nickname = nickname,
                isButtonEnabled = isValid,
                isNicknameError = isValid && nickname.isNotBlank()
            )
        }
    }

    private fun onImageSelected(uri: Uri, multipartImage: MultipartImage) {
        _state.update { it.copy(imageUri = uri, multipartImage = multipartImage) }
    }

    private fun onImageClick() {
        viewModelScope.launch {
            _sideEffect.emit(ProfileSideEffect.OpenGallery)
        }
    }

    private fun onCompleteClick() {
        try {
            Timber.d("onCompleteClick: ")

            viewModelScope.launch {
                val multipartImage = _state.value.multipartImage
                val profile = User(nickname = _state.value.nickname)
                userUseCases.updateProfileUseCase(multipartImage, profile).collect {
                    if (it is ResultType.Success) {
                        _sideEffect.emit(ProfileSideEffect.NavigateToHome)
                    } else {

                    }

                }
            }
        }catch (e: Exception) {
            Timber.e(e, "onCompleteClick: ")
        }
    }

    private fun isNicknameValid(nickname: String) = nickname.length in 2..10

}