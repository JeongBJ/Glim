package com.jeongbj.domain.block.usecase

import javax.inject.Inject

data class BlockUseCases @Inject constructor(
    val blockQuoteUseCase: BlockQuoteUseCase,
    val blockUserUseCase: BlockUserUseCase,
    val unblockQuoteUseCase: UnblockQuoteUseCase,
    val unblockUserUseCase: UnblockUserUseCase,
    val getBlockedQuotesUseCase: GetBlockedQuotesUseCase,
    val getBlockedUsersUseCase: GetBlockedUsersUseCase
)
