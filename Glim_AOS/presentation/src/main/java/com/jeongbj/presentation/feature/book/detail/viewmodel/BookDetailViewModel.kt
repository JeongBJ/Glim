package com.jeongbj.presentation.feature.book.detail.viewmodel

import androidx.lifecycle.ViewModel
import com.jeongbj.domain.book.usecase.BookUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val bookUseCases: BookUseCases
): ViewModel() {

}