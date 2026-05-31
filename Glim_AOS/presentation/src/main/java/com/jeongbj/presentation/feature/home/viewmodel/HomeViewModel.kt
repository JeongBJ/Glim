package com.jeongbj.presentation.feature.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.jeongbj.core.common.ResultType
import com.jeongbj.domain.book.model.BookSearchQueryType
import com.jeongbj.domain.book.usecase.BookUseCases
import com.jeongbj.presentation.feature.home.HomeAction
import com.jeongbj.presentation.feature.home.HomeState
import com.jeongbj.presentation.feature.book.search.paging.BookPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
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

    init {
        getHomeData()
    }

    fun getHomeData() {
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

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.OnRefreshing -> {
                getHomeData()
            }

            is HomeAction.OnBookClick -> {

            }

            is HomeAction.OnQuoteClick -> {

            }
        }
    }
}