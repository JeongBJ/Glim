package com.jeongbj.domain.quote.usecase

import javax.inject.Inject

data class QuoteUseCases @Inject constructor(
    val saveQuoteUseCase: SaveQuoteUseCase,
    val generateImageUseCase: GenerateImageUseCase,
    val getQuotesUseCase: GetQuotesUseCase,
    val getQuoteUseCase: GetQuoteUseCase,
    val increaseViewUseCase: IncreaseViewUseCase,
    val likeQuoteUseCase: LikeQuoteUseCase,
    val blockQuoteUseCase: BlockQuoteUseCase,
    val deleteQuoteUseCase: DeleteQuoteUseCase
)