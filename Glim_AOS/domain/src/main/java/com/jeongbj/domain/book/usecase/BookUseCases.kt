package com.jeongbj.domain.book.usecase

import javax.inject.Inject

data class BookUseCases @Inject constructor(
    val searchBookUseCase: SearchBookUseCase,
    val searchBookByIsbn13: SearchBookByIsbn13UseCase,
    val getHomeDataUseCase: GetHomeDataUseCase
)
