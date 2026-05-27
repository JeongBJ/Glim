package com.jeongbj.presentation.feature.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.jeongbj.domain.book.model.BookSearchQueryType
import com.jeongbj.domain.book.usecase.BookUseCases
import com.jeongbj.domain.book.usecase.SearchBookUseCase
import com.jeongbj.presentation.feature.home.paging.BookPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val bookUseCases: BookUseCases
) : ViewModel() {

    fun searchBook(query: String, type: BookSearchQueryType) = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { BookPagingSource(
            searchBookUseCase = bookUseCases.searchBookUseCase,
            query = query,
            type = type
        ) }
    ).flow.cachedIn(viewModelScope)
}