package com.jeongbj.domain.quote.usecase

import javax.inject.Inject

data class QuoteUseCases @Inject constructor(
    val saveQuoteUseCase: SaveQuoteUseCase,
    val generateImageUseCase: GenerateImageUseCase
)
