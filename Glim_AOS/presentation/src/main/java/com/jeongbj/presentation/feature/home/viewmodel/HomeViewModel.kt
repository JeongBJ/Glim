package com.jeongbj.presentation.feature.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeongbj.core.common.ResultType
import com.jeongbj.domain.book.usecase.BookUseCases
import com.jeongbj.presentation.feature.home.HomeAction
import com.jeongbj.presentation.feature.home.HomeSideEffect
import com.jeongbj.presentation.feature.home.HomeState
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
class HomeViewModel @Inject constructor(
    private val bookUseCases: BookUseCases,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<HomeSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    init {
        getHomeData()
    }

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.OnRefreshing -> {
                getHomeData()
            }

            is HomeAction.OnBookClick -> {
                navigateToBookDetail(action.isbn13)
            }

            is HomeAction.OnQuoteClick -> {

            }
        }
    }

    private fun navigateToBookDetail(isbn13: String) {
        viewModelScope.launch {
            _sideEffect.emit(HomeSideEffect.NavigateToBookDetail(isbn13))
        }
    }

    private fun getHomeData() {
        viewModelScope.launch {
            try {
                bookUseCases.getHomeDataUseCase().collect { result ->
                    when (result) {
                        is ResultType.Success -> {
                            val data = result.data
                            _uiState.update {
                                it.copy(
                                    bestSeller = data.bestSeller,
                                    editorChoice = data.editorChoice,
                                    newSpecial = data.newSpecial,
                                    isLoading = false
                                )
                            }
                        }
                        is ResultType.Loading -> {
                            _uiState.update {
                                it.copy(isLoading = true)
                            }
                        }

                        is ResultType.Error -> {

                        }
                    }
                }
            } catch (e: Exception) {
                Timber.e(e, "getHomeData: ")
            }

        }
    }
}