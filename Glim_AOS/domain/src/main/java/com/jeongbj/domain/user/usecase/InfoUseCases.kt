package com.jeongbj.domain.user.usecase

import javax.inject.Inject

data class InfoUseCases @Inject constructor(
    val getUserInfoUseCase: GetUserInfoUseCase,
    val getLikedQuotesUseCase: GetLikedQuotesUseCase,
    val getMyQuotesUseCase: GetMyQuotesUseCase,
    val getUserLikedQuotesUseCase: GetUserLikedQuotesUseCase,
    val getUserQuotesUseCase: GetUserQuotesUseCase
)
